import styled from 'styled-components';
import { Mode } from '../Menu/ColorMode/ColorMode';

export const Logo = styled.img`
    height: min(80%, 10vw);
    margin: 5% 15px;
    transition: all .5s ease;
    filter: invert(${({ theme }) => theme.colorMode.name === Mode.LIGHT ? "0" : "1"});
`

export const LogoDiv = styled.div`
    height: 100%;
    display: flex;
    gap: 1rem;
    align-items: center;
`