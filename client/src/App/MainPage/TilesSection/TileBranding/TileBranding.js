import { React } from 'react';
import * as S from './styles';
import { ValueDiv, Title, Subtitle, TitleContainer, SubtitleContainer, TileUrl, TileImg} from '../TilesComponents/TilesComponents';
import logoMain from './WMS_logo_main.png';

export const TileBranding = ({ data }) => {
    let {title, subtitle, value, alt, url} = data.content

    return (
        <S.TileBranding className='branding'>
            <TitleContainer>
                <Title>
                    { title }
                </Title>
            </TitleContainer>
            {data.type === 'IMAGE' ?
                <TileImg src={ logoMain } alt={ alt }/>
                :
                <ValueDiv>{ value }</ValueDiv>
            }
            {subtitle &&
                <SubtitleContainer>
                    <Subtitle>
                        { subtitle }
                    </Subtitle>
                </SubtitleContainer>}
            {url && 
            <TileUrl href={ url } target='_blank' rel="noreferrer"/>}
        </S.TileBranding>
    );
};