import styled from 'styled-components';

export const ValueDiv = styled.div`
    width: 100%;
    flex-grow: 1;
    flex-basis: 30%;
    max-height: 30%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;

    font-size: clamp(1.5rem, ${({ isValueLong }) => isValueLong ? '6vw, 2rem' : '8vw, 3rem'});
`;

export const Value = styled.div`
    width: 50%;
    height: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
`;

export const TitleContainer = styled.div`
    width: 100%;
    height: 40%;
    flex-basis: 40%;

    display: flex;
    flex-direction: column;
    overflow: hidden;
    padding-bottom: 0;
    box-sizing: border-box;
    text-align: left;

    @media (max-width: 1000px) {
        .branding & {
            height: 35%;
        }
    }

    font-size: clamp(min(0.7rem, 2.5vw), ${({ titleLength }) => -titleLength * 0.012 + 1.2 }rem, 1.5rem);
`;

export const Title = styled.div`
    height: 100%;
    width: 100%;
    font-weight: 400;
    font-family: 'JetBrains Mono';
`;

export const SubtitleContainer = styled(TitleContainer)`
    position: relative;
    max-height: 30%;
    flex-basis: 30%;
    flex-grow: 1;
    overflow: hidden;

    font-size: clamp(0.5rem, ${({ subTitleLength }) => -subTitleLength * 0.0033 + 0.9 }rem, 0.8rem);

    &::before {
        content: "(···)";
        position: absolute;
        top: 50%;
        left: 50%;
        translate: -50% -50%;
        font-size: 1.2rem;
        opacity: 0.5;
        transition: .2s;
    }

    div:hover > &::before, .branding &::before {
        opacity: 0;    
    }
`

export const Subtitle = styled.div`
    text-align: center;
    height: min-content;
    margin-top: auto;

    opacity: 0;
    transition: .2s;

    div:hover > div > &, .branding & {
        opacity: 1;    
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
    transition: all .5s ease;
    object-fit: contain;
    margin: auto;
`;