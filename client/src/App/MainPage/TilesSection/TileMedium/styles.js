import styled from 'styled-components';
import { Tile } from '../styles.js';

export const TileMedium = styled(Tile)`
    grid-column: span 2;
    grid-row: span 1;
    background-color: ${({ theme }) => theme.colorMode.tileMedium.backgroundColor};
    box-shadow: 3px 3px 15px 0px #00000012;
`;
