import styled from "styled-components";
import { ReactComponent as enFlag} from './en.svg';
import { ReactComponent as plFlag } from './pl.svg';
import i18n from "../../../../../Language";

const shown = `
    opacity: 1;
    z-index: 10;
`

const hidden = `
    opacity: 0;
    z-index: 0;
`

export const FlagButton = styled.input.attrs({ type: 'checkbox' })`
    display: none;
    visibility: hidden;

    &:checked + label {
        transform: rotate(-360deg);
    }
`

export const FlagLabel = styled.label`
    position: relative;
    display: inline-block;
    height: 1.5rem;
    width: 1.5rem;
    transition: all .5s ease;
    transform-origin: center;
    cursor: pointer;
    user-select: none;

    & svg {
        position: absolute;
        top: 0;
        left: 0;
        height: 100%;
        width: 100%;
        transform-origin: center;
        transition: inherit;
    }
`


export const StyledEnFlag = styled(enFlag)`
    ${ () => i18n.language === 'en' ? shown : hidden }
`;

export const StyledPlFlag = styled(plFlag)`
    ${ () => i18n.language === 'pl' ? shown : hidden }
`;



