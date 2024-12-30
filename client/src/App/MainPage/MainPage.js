import React, {Fragment} from 'react';
import {TilesSection} from "./TilesSection/TilesSection.js" 
import {Header} from './Header/Header.js';
import {Footer} from './Footer/Footer.js';
import { FiltersProvider } from './FiltersContext.js';
import * as S from './styles';

export const MainPage = () => {
    return(
        <Fragment>
            <S.MainPage>
                <FiltersProvider>
                    <Header loggedIn={true}/>
                    <TilesSection/>
                    <Footer/>
                </FiltersProvider>
            </S.MainPage>
        </Fragment>
    );
}