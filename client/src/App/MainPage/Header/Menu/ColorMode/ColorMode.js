import * as S from "./styles";
import { Fragment, useContext } from "react";
import { ColorModeContext } from "../../../../../App";
import { useTranslation } from "react-i18next";

export const Mode = Object.freeze({
    LIGHT: Symbol("light"),
    DARK:  Symbol("dark")
  });

const LightThemeIcon = () => (
    <S.ThemeIcon viewBox="0 0 24 24" className='light'>
        <path
            d="M12,17c-2.76,0-5-2.24-5-5s2.24-5,5-5,5,2.24,5,5-2.24,5-5,5Zm1-13V1c0-.55-.45-1-1-1s-1,.45-1,1v3c0,.55,.45,1,1,1s1-.45,1-1Zm0,19v-3c0-.55-.45-1-1-1s-1,.45-1,1v3c0,.55,.45,1,1,1s1-.45,1-1ZM5,12c0-.55-.45-1-1-1H1c-.55,0-1,.45-1,1s.45,1,1,1h3c.55,0,1-.45,1-1Zm19,0c0-.55-.45-1-1-1h-3c-.55,0-1,.45-1,1s.45,1,1,1h3c.55,0,1-.45,1-1ZM6.71,6.71c.39-.39,.39-1.02,0-1.41l-2-2c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l2,2c.2,.2,.45,.29,.71,.29s.51-.1,.71-.29Zm14,14c.39-.39,.39-1.02,0-1.41l-2-2c-.39-.39-1.02-.39-1.41,0s-.39,1.02,0,1.41l2,2c.2,.2,.45,.29,.71,.29s.51-.1,.71-.29Zm-16,0l2-2c.39-.39,.39-1.02,0-1.41s-1.02-.39-1.41,0l-2,2c-.39,.39-.39,1.02,0,1.41,.2,.2,.45,.29,.71,.29s.51-.1,.71-.29ZM18.71,6.71l2-2c.39-.39,.39-1.02,0-1.41s-1.02-.39-1.41,0l-2,2c-.39,.39-.39,1.02,0,1.41,.2,.2,.45,.29,.71,.29s.51-.1,.71-.29Z"
        />
    </S.ThemeIcon>
)

const DarkThemeIcon = () => (
    <S.ThemeIcon viewBox="0 0 24 24" className='dark'>
        <path
            d="M16.968,8.532,19.3,7.365l1.167-2.333,1.167,2.333,2.333,1.167L21.635,9.7l-1.167,2.333L19.3,9.7ZM23,17a1,1,0,0,0,0,2A1,1,0,0,0,23,17Zm-6-4a1,1,0,0,0,0,2A1,1,0,0,0,17,13Zm1.76,8.894,1.466-1.006-1.621-.731c-7.013-3.036-7.5-13.218-.8-16.906l1.542-.88-1.552-.862C10.032-2.985-.151,3.044,0,12A11.975,11.975,0,0,0,18.76,21.894Z"
        />
    </S.ThemeIcon>
)

export const ColorMode = () => {
    const colorMode = useContext(ColorModeContext);
    const { t } = useTranslation();
    return (
        <Fragment>
            <S.ThemeButton id="theme-button" onClick={colorMode.switchColorMode}/>
            <S.ThemeLabel htmlFor="theme-button" data-desc={t('menuTipColorMode')}>
                <LightThemeIcon />
                <DarkThemeIcon />
            </S.ThemeLabel>
        </Fragment>
    );
}