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
    font-weight: italic;
    text-align: center;
`;

export const Heading = styled.div`
    height: 20%;
    padding: 1.5rem 4vh 0;
    text-align: center;

    @media (max-width: 1000px) {
        padding: 1rem 1.5vh 0;
        height: 25%;
    }
`;