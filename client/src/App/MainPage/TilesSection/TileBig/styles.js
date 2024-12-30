import styled from 'styled-components';
import { Tile } from '../styles.js';

export const TileBig = styled(Tile)`
    grid-column: span 3;
    grid-row: span 2;
    z-index: 1;
    height: auto;
    box-shadow: 3px 3px 15px 3px #00000012;

    @media (max-width: 1000px) {
        grid-column: span 2;
    }
`;
