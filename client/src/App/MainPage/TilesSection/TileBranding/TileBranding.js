import { React, useContext } from 'react';
import * as S from './styles';
import { ValueDiv, Title, Subtitle, TitleContainer, SubtitleContainer, TileUrl, TileImg} from '../TilesComponents/TilesComponents';
import { ColorModeContext } from '../../../../App';
import { Mode } from '../../Header/Menu/ColorMode/ColorMode';
import logoMain from './WMS_logo_main.png';
import logoDarkMode from './WMS_logo_darkmode.png';

export const TileBranding = ({ data }) => {
    let {title, subtitle, value, alt, url} = data.content
    const { colorMode } = useContext(ColorModeContext);
    const imagePath = colorMode === Mode.DARK ? logoDarkMode : logoMain;

    return (
        <S.TileBranding className='branding'>
            <TitleContainer>
                <Title>
                    { title }
                </Title>
            </TitleContainer>
            {data.type === 'IMAGE' ?
                <TileImg src={ imagePath } alt={ alt }/>
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