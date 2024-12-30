import styled from "styled-components";
import { Mode } from "./ColorMode";

const shown = `
    opacity: 1;
    z-index: 10;
`

const hidden = `
    opacity: 0;
    z-index: 0;
`

export const ThemeButton = styled.input.attrs({ type: 'checkbox' })`
    display: none;
    visibility: hidden;

    &:checked + label {
        transform: rotate(360deg);
    }
`

export const ThemeLabel = styled.label`
    position: relative;
    display: inline-block;
    height: 1.5rem;
    width: 1.5rem;
    transition: all .5s ease;
    transform-origin: center;
    cursor: pointer;
    user-select: none;
`

export const ThemeIcon = styled.svg.attrs({
    version: '1.1',
    xmlns: 'http://www.w3.org/2000/svg',
    xmlnsXlink: 'http://www.w3.org/1999/xlink',
  })`
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    transform-origin: center;
    transition: inherit;
    fill: ${({ theme }) => theme.colorMode.fontColor};

    & path {
        transition: inherit;
    }

    &.light {
        & path {
            ${({ theme }) => theme.colorMode.name === Mode.LIGHT ? shown : hidden}
        }
    }

    &.dark {
        & path {
            ${({ theme }) => theme.colorMode.name === Mode.DARK ? shown : hidden}
        }
    }
  `;

