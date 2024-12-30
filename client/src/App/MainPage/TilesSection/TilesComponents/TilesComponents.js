import styled from 'styled-components';

export const ValueDiv = styled.div`
    width: 100%;
    height: 35%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;

    font-size: 4vw;
    ${({ isValueLong }) => isValueLong && `
            font-size: 2.2vw;
        `}

    @media (max-width: 1000px) {
        height: 15%;
        font-size: 5vh;
    }
`

export const Value = styled.div`
    width: 50%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 50px;
    text-align: center;
`;

export const TitleContainer = styled.div`
    width: 100%;
    height: 45%;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    padding: 1rem;
    padding-bottom: 0;
    box-sizing: border-box;
    font-size: 1rem;
    ${({ isTitleLong }) => isTitleLong && `
            font-size: 0.65rem;
    `}

    @media (min-width: 1400px) and (max-height: 1000px) {
        ${({ isTitleLong }) => isTitleLong && `
            font-size: 0.75rem;
        `}
    }

    @media (min-width: 1000px) and (max-height: 560px) {
        font-size: 0.8rem;
        ${({ isTitleLong }) => isTitleLong && `
            font-size: 0.7rem;
        `}
    }

    @media (min-width: 1000px) and (min-height: 1000px) {
        font-size: 1.2rem;
    }

    @media (min-width: 1600px) {
        font-size: 1.2rem;
    }

    @media (min-width: 2000px) and (min-height: 1100px) {
        font-size: 1.5rem;
        padding: 2rem;
    }

    &::-webkit-scrollbar {
        width: 13px;
    }

    &::-webkit-scrollbar-thumb {
        background-color: grey;
        border-radius: 10px;
        border: 3px solid transparent;
    }

    &::-webkit-scrollbar-track {
        height: 10px;
        background-color: transparent;
    }

    text-align: left;

    @media (max-width: 1000px) {
        padding: .5rem;
        height: 50%;
        .branding & {
            height: 35%;
        }
    }
`;

export const Title = styled.div`
    height: 100%;
    width: 100%;
    font-weight: 400;
    font-family: 'JetBrains Mono';
`;

export const SubtitleContainer = styled(TitleContainer)`
    height: 18%;
    padding: 6px;
    display: flex;
    justify-content: center;
    @media (max-width: 1000px) {
        height: 35%;
    }
`

export const Subtitle = styled.div`
    font-size: 1vw;
    text-align: center;
    height: min-content;


    @media (max-width: 1000px) {
        font-size: 0.9rem;
    }

    @media (max-width: 500px) {
        font-size: 0.7rem;
    }
`

export const TileUrl = styled.a`
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
`;

export const TileImg = styled.img`
    max-height: 40%;
    max-width: 90%;
    width: auto;
    height: auto;
`;