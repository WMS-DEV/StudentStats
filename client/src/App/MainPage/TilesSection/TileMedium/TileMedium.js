import React from 'react';
import * as S from './styles';
import { ValueDiv, Title, Subtitle, TitleContainer, SubtitleContainer, Value } from '../TilesComponents/TilesComponents';
import { useState } from 'react';
import { useEffect } from 'react';

export const TileMedium = ({data}) => {
    const {title, subtitle, value1, value2} = data.content

    return (
        <S.TileMedium>
            <TitleContainer>
                <Title>
                    { title }
                </Title>
            </TitleContainer>
            <ValueDiv>
                <Value>{ value1 }</Value>
                <Value>{ value2 }</Value>
            </ValueDiv>
            {subtitle &&
            <SubtitleContainer>
                <Subtitle>
                    { subtitle }
                </Subtitle>
            </SubtitleContainer>}
            {/* <ValuesContainer>
                <Value>{ value1 }</Value>
                <Value>{ value2 }</Value>
            </ValuesContainer> */}
        </S.TileMedium>
    )
}