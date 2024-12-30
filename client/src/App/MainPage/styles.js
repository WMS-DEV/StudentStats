import styled from 'styled-components';

export const MainPage = styled.div`
    //background-image: linear-gradient(120deg, #fdfbfb 0%, #ebedee 100%);
    //background-image: linear-gradient(60deg, #29323c 0%, #485563 100%);
    background-color: ${({ theme }) => theme.colorMode.tilesSection.backgroundColor};
    background-blend-mode: multiply;
    transition: background-color .5s ease, color .5s ease;
    z-index: 10;
    overflow: hidden;
`;