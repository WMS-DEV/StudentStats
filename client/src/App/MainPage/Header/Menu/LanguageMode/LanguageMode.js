import * as S from "./styles";
import { Fragment, useState } from "react";
import i18n from '../../../../../Language.js';
import { useAuth } from '../../../../../AuthorizationProvider.js';
import { useTranslation } from "react-i18next";

export const LanguageMode = () => {
    const [_, setTemp] = useState();
    const { isDataLoading, refreshUserData } = useAuth();
    const { t } = useTranslation();

    const cantChangeLanguage = isDataLoading && window.location.pathname === '/dashboard';

    const switchLanguageMode = () => {
        if(cantChangeLanguage) return;
        if(i18n.language === 'en') {
            i18n.changeLanguage('pl');
            localStorage.setItem('languageMode', 'pl');
        } else {
            i18n.changeLanguage('en');
            localStorage.setItem('languageMode', 'en');
        }
        refreshUserData();
        setTemp({});
    }

    return (
        <Fragment>
            <S.FlagButton id="lang-button" onClick={switchLanguageMode} disabled={cantChangeLanguage}/>
            <S.FlagLabel htmlFor="lang-button" data-desc={t('menuTipLanguageMode')}>
                <S.StyledEnFlag />
                <S.StyledPlFlag />
            </S.FlagLabel>
        </Fragment>
    );
}