import styled from 'styled-components';

export const Line = styled.div`
    height: 70%;
    display: flex;
    align-items: center;
    justify-content: center;

    @media (max-width: 1000px) {
        height: 60%;
    }
`;

export const LineChart = styled.div`
    height: 100%;
    z-index: 2;
    transition: background-color .2s ease-in-out;
    background-color: ${({ theme }) => theme.colorMode.tileBigLine.backgroundColor};
`;

export const Subheading = styled.div`
    height: 10%;
    padding: 0;
    font-size: 2.75vh;
    font-weight: italic;
    text-align: center;
`;

export const Heading = styled.div`
    height: 20%;
    padding: 5vh 4vh 0;
    font-size: 3.25vh;
    text-align: center;

    @media (max-width: 1000px) {
        padding: 3vh 1.5vh 0;
        font-size: 3vh;
        height: 25%;
    }
`;