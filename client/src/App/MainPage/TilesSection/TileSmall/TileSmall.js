import React from 'react';
import * as S from './styles';
import { ValueDiv, Title, Subtitle, TitleContainer, SubtitleContainer } from '../TilesComponents/TilesComponents';

export const TileSmall = ({ data }) => {
    let { title, subtitle, value } = data.content
    let isTileBoolean = false;
    let isTitleLong = title.length > 40;
    let isValueLong = value.length > 6;
    if (typeof value === 'boolean') {
        value = value ? 'Tak' : 'Nie'
        isTileBoolean = true
    }
    return (
        <S.TileSmall isTileBoolean={isTileBoolean}>
            <TitleContainer isTitleLong={isTitleLong}>
                <Title>
                    { title }
                </Title>
            </TitleContainer>
            <ValueDiv isValueLong={isValueLong}>{ value }</ValueDiv>
            {subtitle &&
                <SubtitleContainer>
                    <Subtitle>
                        { subtitle }
                    </Subtitle>
                </SubtitleContainer>}
        </S.TileSmall>
    );
};