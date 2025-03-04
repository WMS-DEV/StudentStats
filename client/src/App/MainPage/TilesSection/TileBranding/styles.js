import styled from 'styled-components';
import { TileSmall } from '../TileSmall/styles.js';
import { Mode } from '../../Header/Menu/ColorMode/ColorMode';

export const TileBranding = styled(TileSmall)`
    & img {
       filter: invert(${({ theme }) => theme.colorMode.name === Mode.LIGHT ? "0" : "1"});
    }
    @media (max-width: 1000px) {
        order: 999;
        grid-column: span 2;
    }
`;
