import styled from 'styled-components';
import { Tile } from '../styles.js';

export const TileSmall = styled(Tile)`
    grid-column: span 1;
    grid-row: span 1;
    text-align: center;
    background-color: ${({ theme }) => (props) => props.isTileBoolean ? theme.colorMode.tileSmallBoolean.backgroundColor : theme.colorMode.tileSmall.backgroundColor};
    box-shadow: 3px 3px 15px 0px #00000012;
`;