import styled from 'styled-components';

export const Menu = styled.div`
    height: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;

    & > * {
        margin-right: 3rem;
    }

    @media screen and (max-width: 500px) {
        & > * {
            margin-right: 7vw;
        }
    }

    & > *::before {
        content: attr(data-desc);
        text-wrap: nowrap;
        position: absolute;
        width: fit-content;
        height: 100%;
        top: 120%;
        left: 50%;
        translate: -50% 0;

        padding: 1rem;

        line-height: 20%;
        color: ${({ theme }) => theme.colorMode.fontColor};
        background-color: ${({ theme }) => theme.colorMode.header.backgroundColor};
        border: 1px solid ${({ theme }) => theme.colorMode.header.borderColor};
        border-radius: .5rem;
        box-shadow: 0 1px 2px black;
        z-index: 100000;

        opacity: 0;

        transition: 0.2s;
    }

    @media (hover: hover) and (pointer: fine) {
        & > *:hover::before {
            opacity: 1;
        }
    }
`