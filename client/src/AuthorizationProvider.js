import React, { createContext, useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiLink } from "./GlobalVariables";
import { useTranslation } from "react-i18next";

const AuthContext = createContext(null);

// error enum
export const ErrorMessageCode = {
    errorUnknown: "errorUnknown",
    errorNonActiveNoData: "errorNonActiveNoData",
    errorNoData: "errorNoData",
    errorWrongLogin: "errorWrongLogin",
}

export const RequestLoginAndRedirect = async () => {
  let headers = new Headers();
  headers.append("Content-Type", "application/json");

  let requestOptions = {
    method: "POST",
    headers: headers,
    body: JSON.stringify({
      universityId: localStorage.getItem("universityId"),
    }),
  };

  fetch(`${apiLink}/auth/request-token`, requestOptions)
    .then((response) => response.json())
    .then((requestTokenResponse) => {
      localStorage.setItem("requestToken", requestTokenResponse.requestToken);
      localStorage.setItem("tokenSecret", requestTokenResponse.tokenSecret);
      window.location.assign(requestTokenResponse.authorizationUrl);
    })
    .catch((error) => {
      console.log(error);
    });
};

export const getData = async (JWT) => {
  if (JWT === "demo") return await getDemoData();
  const headers = new Headers();
  headers.append("Content-Type", "application/json");
  const savedLanguage = localStorage.getItem("languageMode");
  headers.append("Accept-Language", savedLanguage ? savedLanguage : "en");
  headers.append("Authorization", `Bearer ${JWT}`);

  const requestOptions = {
    method: "GET",
    headers: headers,
  };

  console.log("Data download");
  return await fetch(`${apiLink}/getData`, requestOptions)
    .then((response) => {
        if (response.status === 404) {
          throw new Error(ErrorMessageCode.errorNonActiveNoData);
        } else if (response.status !== 200) {
          throw new Error(ErrorMessageCode.errorUnknown);
        }
        return response.json()
    })
    .catch((error) => {
      console.log(error);
      throw error; // rethrow the error to be handled in the calling function
    });
};

export const getGrades = async (JWT) => {
  let headers = new Headers();
  headers.append("Content-Type", "application/json");
  headers.append("Authorization", `Bearer ${JWT}`);

  let requestOptions = {
    method: "GET",
    headers: headers,
  };

  const endpoint = JWT === "demo" ? "downloadMockCsv" : "downloadCsv";

  return await fetch(`${apiLink}/${endpoint}`, requestOptions)
    .then((response) => response.blob())
    .catch((error) => {
      console.log(error);
    });
};

export const AuthDemo = async (username, password) => {
  let headers = new Headers();
  headers.append("Content-Type", "application/json");

  let requestOptions = {
    method: "GET",
    headers: headers,
  };

  return fetch(`${apiLink}/getStaticMockedData`, requestOptions)
    .then((response) => {
      if (response.ok) return response.json();
      else throw new Error(response.status);
    })
    .catch((error) => {
      console.log(error);
      return undefined;
    });
};

export const getDemoData = async () => {
  const headers = new Headers();
  headers.append("Content-Type", "application/json");

  const savedLanguage = localStorage.getItem("languageMode");
  headers.append("Accept-Language", savedLanguage ? savedLanguage : "en");

  const requestOptions = {
    method: "GET",
    headers: headers,
  };

  return await fetch(`${apiLink}/getStaticMockedData`, requestOptions)
    .then((response) => {
      if (response.ok) return response.json();
      else throw new Error(response.status);
    })
    .catch((error) => {
      console.log(error);
      return undefined;
    });
};

export const useAuth = () => {
  return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();
  const { i18n } = useTranslation();
  const [userData, setUserData] = useState(null);
  const [errorMessageCode, setErrorMessageCode] = useState(null);

  const isLogged = localStorage.getItem('jwt') ? true : false;  
  const isDataLoading = isLogged && !userData;
  const isError = errorMessageCode !== null;

  useEffect(() => {
    refreshUserData();
  }, []);

  const getUniversities = async () => {
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    headers.append("Accept-Language", i18n.language);

    let requestOptions = {
      method: "GET",
      headers: headers,
    };

    return await fetch(`${apiLink}/universities`, requestOptions)
      .then((response) => {
        return response.status === 403 ? 403 : response.json();
      })
      .catch((error) => console.log(error));
  };

  const handleUniversityChange = (event) => {
    localStorage.setItem("universityId", event.target.value);
  };

  const handleLoginRequest = async () => {
    await RequestLoginAndRedirect();
  };

  const handleLogout = () => {
    console.log("Logged out");
    const storedTheme = localStorage.getItem("colorMode");
    const storedLanguage = localStorage.getItem("languageMode");
    localStorage.clear();
    if (storedTheme) localStorage.setItem("colorMode", storedTheme);
    if (storedLanguage) localStorage.setItem("languageMode", storedLanguage);
    setUserData(null);
    navigate("/loginpage");
  };

  const AuthAndGetJWT = async (verifier) => {
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
  
    let requestOptions = {
      method: "POST",
      headers: headers,
      body: JSON.stringify({
        requestToken: localStorage.getItem("requestToken"),
        tokenSecret: localStorage.getItem("tokenSecret"),
        verifier: verifier,
        universityId: localStorage.getItem("universityId"),
      }),
    };
  
    return await fetch(`${apiLink}/auth/access-token`, requestOptions)
      .then((response) => response.json())
      .then((accessResponse) => accessResponse.jwt)
      .catch((error) => {
          console.log(error)
          setErrorMessageCode(ErrorMessageCode.errorUnknown);
      });
  };

  const handleDataDownload = async () => {
    const savedJWT = localStorage.getItem("jwt");
    if (savedJWT) {
        try {
            const uData = await getData(savedJWT);
            if (uData !== undefined) {
                setUserData(uData);
                if(uData && uData.hasOwnProperty('content') && uData.content.length <= 1) {
                    setErrorMessageCode("errorNoData");
                }
                navigate("/dashboard");
            }
            else handleLogout();
        } catch (error) {
            if (error && error.message === ErrorMessageCode.errorNonActiveNoData) {
                setUserData({content: [], personalData: {studentStatus: "FALSE"}});
                setErrorMessageCode(ErrorMessageCode.errorNonActiveNoData);
                navigate("/dashboard");
            } else {
                console.error("Error fetching data:", error);
                setErrorMessageCode(ErrorMessageCode.errorUnknown);
                navigate("/dashboard");
            }
        }
    } else navigate("/loginpage");
  };

  const refreshUserData = async () => {
    if (window.location.pathname !== "/dashboard") return;
    setUserData(null);
    handleDataDownload();
  };

  const handleLoginDemo = async (event) => {
    localStorage.setItem("jwt", "demo");

    const uData = await getDemoData();
    setUserData(uData); // change this to setUserData({content: []}); to see empty message
    navigate(userData !== undefined ? "/dashboard" : "/loginpage");
  };

  const handleLoginAccess = async (verifier) => {
    const savedJWT = localStorage.getItem("jwt");
    const JWT = savedJWT ? savedJWT : await AuthAndGetJWT(verifier);
    localStorage.setItem("jwt", JWT);

    handleDataDownload();
  };

  const value = {
    isDataLoading,
    isError,
    errorMessageCode,
    userData,
    refreshUserData: refreshUserData,
    onLoginClick: handleLoginRequest,
    onLoginCallback: handleLoginAccess,
    onLoginDemo: handleLoginDemo,
    onLogout: handleLogout,
    onUniversityChange: handleUniversityChange,
    getUniversities: getUniversities,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const ProtectedRoute = ({ children }) => {
  const navigate = useNavigate();

  if (localStorage.getItem("jwt") === null) {
    navigate("/loginpage");
  }

  return children;
};
