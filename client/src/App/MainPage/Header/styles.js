import styled from 'styled-components';

export const Header = styled.div`
    width: 100%;
    height: 4rem;
    background-color: ${({ theme }) => theme.colorMode.header.backgroundColor};
    display: flex;
    justify-content: space-between;
    position: fixed;
    border-bottom: 1px solid black;
    transition: background-color .5s ease, color .5s ease;
    z-index: 10;

    // for testing purposes
    h2 {
        color: ${({ theme }) => theme.colorMode.fontColor};
        height: 100%;
        font-size: 3rem;
        display: flex;
        align-items: center;
        margin-left: 2rem;
        transition: color .5s ease;

        @media (max-width: 1000px) {
            font-size: 2rem;
            margin-right: 10px;
        }
    }
`;