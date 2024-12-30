import styled from "styled-components";

export const Popover = styled.div`
    background-color: ${({ theme }) => theme.colorMode.popover.backgroundColor};
    color: ${({ theme }) => theme.colorMode.fontColor};
    width: auto;
    min-width: 15rem;
    height: auto;
    border-radius: .5rem;
    position: absolute;
    box-shadow: 8px 8px 5px rgba(0, 0, 0, 0.1);
    right: 0;
    top: 70px;
    padding: 1rem;
    margin-left: 0.5rem;
    display: flex;
    flex-direction: column;
    transition: background-color 0.3s, color 0.3s;

    @keyframes inAnimation {
        0% {
            opacity: 0;
            visibility: hidden;
        }
        100% {
            opacity: 1;
            visibility: visible;
        }
    }

    @keyframes outAnimation {
        0% {
            opacity: 1;
        }
        100% {
            opacity: 0;
            visibility: hidden;
        }
    }

    @media screen and (max-width: 1024px) {
        min-width: 12rem;
    }
`