import * as S from "./styles";
import { Fragment, useState } from "react";
import i18n from '../../../../../Language.js';
import { useAuth } from '../../../../../AuthorizationProvider.js';


export const LanguageMode = () => {
    const [_, setTemp] = useState();
    const { userData, refreshUserData } = useAuth();

    const isLogged = localStorage.getItem('jwt') ? true : false;

    const cantChangeLanguage = isLogged && !userData && window.location.pathname === '/dashboard';

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
            <S.FlagLabel htmlFor="lang-button">
                <S.StyledEnFlag />
                <S.StyledPlFlag />
            </S.FlagLabel>
        </Fragment>
    );
}