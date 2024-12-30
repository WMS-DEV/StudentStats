import GlobalStyle from './GlobalStyles';
import React, {Fragment, useState, createContext, useEffect} from 'react';
import {LoginPage, LoginPageComponent} from './App/LoginPage/LoginPage.js'
import {Page404, Page404Component} from './App/404Page/404Page.js'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {AuthProvider, ProtectedRoute, } from "./AuthorizationProvider.js";
import {MainPage} from "./App/MainPage/MainPage.js"
import {Mode} from "./App/MainPage/Header/Menu/ColorMode/ColorMode.js";
import i18n from './Language.js';
import Theme from "./Theme";
import * as Sentry from "@sentry/react";
import {BrowserTracing} from "@sentry/react";
import 'bootstrap/dist/css/bootstrap.min.css';

export const ColorModeContext = createContext();

function App() {
    const [colorMode, setColorMode] = useState(
        localStorage.getItem('colorMode') === 'dark' ? 
        Mode.DARK : 
        Mode.LIGHT
    );

    const switchColorMode = () => {
        if(colorMode === Mode.LIGHT) {
            setColorMode(Mode.DARK);
            localStorage.setItem('colorMode', 'dark');
        } else {
            setColorMode(Mode.LIGHT);
            localStorage.setItem('colorMode', 'light');
        }
    }

    useEffect(() => {
        const storedLanguage = localStorage.getItem('languageMode');
        if(storedLanguage) {
            if(i18n.language !== storedLanguage)
                i18n.changeLanguage(storedLanguage);
        }
        else if(navigator.language === 'en') i18n.changeLanguage('en');
        else i18n.changeLanguage('pl');        
    }, []);

  initSentry()
  
  return (
    <Fragment>
      <GlobalStyle />
      <Theme colorMode={colorMode}>
         <ColorModeContext.Provider value={{colorMode, switchColorMode}}>
          <BrowserRouter>
            <AuthProvider>
              <Routes>
                <Route path="dashboard" element={<ProtectedRoute><MainPage/></ProtectedRoute>}></Route>
                <Route path="loginpage" index element={<LoginPage/>} />
                <Route index element={<LoginPage/>} />
                <Route path="/*" element={<Page404/>}></Route>
              </Routes>
            </AuthProvider>
          </BrowserRouter>
        </ColorModeContext.Provider>
      </Theme>
    </Fragment>
  );
}

function initSentry() {
    const sentryDsn = process.env.REACT_APP_SENTRY_DSN || '';
    Sentry.init({
        dsn: sentryDsn,
        integrations: [new BrowserTracing()],
        tracesSampleRate: 1.0,
    });
}

export default App;
