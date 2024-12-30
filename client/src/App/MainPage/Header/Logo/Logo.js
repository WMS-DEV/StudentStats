import * as S from './styles';
import { useContext } from "react";
import { ColorModeContext } from '../../../../App';
import { Mode } from '../Menu/ColorMode/ColorMode';
import logoMain from './logo_main.png';
import logoDarkMode from './logo_darkmode.png';


export const Logo = () => {
    const { colorMode } = useContext(ColorModeContext);
    const imagePath = colorMode === Mode.DARK ? logoDarkMode : logoMain;
    return (
        <S.LogoDiv>
            <S.Logo src={imagePath} alt='logo-wms'/>
        </S.LogoDiv>
    )
}