import styled from 'styled-components';

export const Bar = styled.div`
    height: 77%;
    display: flex;
    align-items: center;
    justify-content: center;
`;

export const BarChart = styled.div`
    height: 100%;
    z-index: 2;
    transition: background-color .2s ease-in-out;
    background-color: ${({ theme }) => theme.colorMode.tileBigBar.backgroundColor};
`;

export const Heading = styled.div`
    height: 20%;
    padding: 5vh 4vh 0;
    font-size: 3.25vh;
    text-align: center;
    color: ${({ theme }) => theme.colorMode.tileBigBar.fontColor};
    font-family: "JetBrains Mono";

    @media (max-width: 1000px) {
        padding: 3vh 1.5vh 0;
        font-size: 3vh;
        height: 25%;
    }
`;