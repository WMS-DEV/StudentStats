import React, { UseContext } from 'react';
import * as S from './styles';
import { Menu } from './Menu/Menu';
import { Logo } from './Logo/Logo';
import { ColorMode } from '../../MainPage/MainPage';

export const Header = ({loggedIn}) => {
    return(
        <S.Header>
            <Logo/>
            <Menu loggedIn={loggedIn}/>
        </S.Header>
    )
}