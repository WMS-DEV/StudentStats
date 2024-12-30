import styled from 'styled-components';
import { TileSmall } from '../TileSmall/styles.js';

export const TileBranding = styled(TileSmall)`
    @media (max-width: 1000px) {
        order: 999;
        grid-column: span 2;
    }
`;
