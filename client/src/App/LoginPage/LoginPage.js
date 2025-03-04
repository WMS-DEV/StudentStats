import React, { useEffect, useState } from 'react';
import { useAuth } from '../../AuthorizationProvider';
import { Header } from '../MainPage/Header/Header.js';
import { HomeBackground } from '../HomeBackground.js';
import { Trans, useTranslation } from "react-i18next";

import * as S1 from '../styles';
import * as S2 from './styles';

const S = {
  ...S1,
  ...S2,
};

export const LoginPage = () => {
  const {
    onLoginClick,
    onLoginCallback,
    onLoginDemo,
    getUniversities,
    onUniversityChange,
  } = useAuth();

  const [isConnectedToServer, setIsConnectedToServer] = useState(true);
  const [usosLoginDisabled, setUsosLoginDisabled] = useState(true);
  const [animationFinished, setAnimationFinished] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const { i18n, t } = useTranslation();

  const handleMouseLeave = (event) => {
    setAnimationFinished(true);
  };

  const handleLoginReal = async (event) => {
    await onLoginClick(event);
  };

  const handleUniversityChange = (event) => {
    onUniversityChange(event);
    setUsosLoginDisabled(false);
  };

  const [universities, setUniversities] = useState([{ "id": "0", "name": t('loginLoading') }]);

  useEffect(() => {
    setIsLoading(false);
    const searchParams = new URLSearchParams(window.location.search);

    // Check if there is verifier (callback from USOS)
    if (searchParams.has("oauth_verifier")) {
      setIsLoading(true);
      onLoginCallback(searchParams.get("oauth_verifier"));
    }
  }, []);

  useEffect(() => {
    getUniversities().then(response => {
      if(!response) return setIsConnectedToServer(false);
      
      setIsConnectedToServer(true);
      setUniversities(response);
      document.querySelector('select').options[0].selected = true
      setUsosLoginDisabled(true);
    });
  }, [i18n.language]);

  return (
    <>
      <Header loggedIn={false} />
      <S.HomePage>
        <HomeBackground />
        {isLoading && <S.LoadingSpinner animation="border" role='status' variant='success' />}
        <S.HomeForm>
          <S.Title>{t('loginWelcome')}</S.Title>
          <S.Selection defaultValue="" onChange={handleUniversityChange} disabled={!isConnectedToServer}>
            <S.Option value="" disabled hidden>{t('loginChooseMonit')}</S.Option>
            {universities.map(university =>
              <S.Option key={university.id} value={university.id}>
                {university.name}
              </S.Option>)}
          </S.Selection>
          <S.LoginPageButton disabled={usosLoginDisabled || !isConnectedToServer} type="button" onClick={handleLoginReal}>{t('loginUSOS')}</S.LoginPageButton>
          <S.LoginPageButton disabled={isLoading || !isConnectedToServer} className="outline" type="button" onClick={onLoginDemo}>{t('loginDemo')}</S.LoginPageButton>
          {!isConnectedToServer && <S.ErrorMessage>{t('loginError')}</S.ErrorMessage>}
        </S.HomeForm>
        <S.InfoBox onMouseLeave={handleMouseLeave} animationFinished={animationFinished}>
          <S.InfoMessage>
            <Trans i18nKey="loginInfo" components={[<a href="https://wmsdev.pl/">WMS_DEV</a>]} />
          </S.InfoMessage>
        </S.InfoBox>
      </S.HomePage>
    </>
  );
};
