import styled from 'styled-components';
// import theme from '../../../Theme';
import { LineChart } from './TileBig/LineChart/styles';
import { BarChart } from './TileBig/BarChart/styles';

export const Tile = styled.div`
    position: relative;
    display: flex;
    flex-direction: column;

    overflow: hidden;
    border-radius: 10px;
    color: ${({ theme }) => theme.colorMode.fontColor};
    height: 100%;
    width: 100%;
    padding: .6rem;
    box-shadow: rgba(0, 0.45, 0.45, 0.45) 0px 15px 20px -20px;
    transition: background-color .2s ease-in-out, color .2s ease-in-out;
    &:hover {
        cursor: pointer;
    }
    animation: fadeTileIn 400ms ease-in-out;

    @keyframes fadeTileIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }

    &::before {
        content: "";
        position: absolute;
        opacity: 0;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        --shimmer-color: ${({ theme }) => theme.colorMode.shimmerColor};
        background: linear-gradient(-45deg, transparent 25%, var(--shimmer-color) 50%, transparent 75%);
        background-size: 200% 100%;
        pointer-events: none;
    }

    &:has(${LineChart}) {
        background: ${({ theme }) => theme.colorMode.tileBigLine.backgroundColor};
    }

    &:has(${BarChart}) {
        background: ${({ theme }) => theme.colorMode.tileBigBar.backgroundColor};
    }
`


export const TilesSection = styled.div(({theme, isDataEmpty}) => `
    padding: 7vh 8vw;
    height: calc(100vh - 4rem);
    width: 100%;
    position: relative;
    top: 4.1rem;

    //Background
    z-index: 1;

    //Tiles
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    // grid-template-rows: repeat(3, 25vh);
    grid-auto-flow: row;
    grid-auto-rows: max(25vh, 10rem);
    row-gap: 2rem;
    column-gap: 2rem;
    @media (max-width: 1000px) {
        column-gap: 1vh;
        row-gap: 2vh;
        grid-template-columns: repeat(2, 1fr);
    }

    overflow: scroll;
    overflow-x: hidden;

    @keyframes shimmer {
        0% {
            background-position: 200% 0;
        }
        100% {
            background-position: -200% 0;
        }
    }

    ${isDataEmpty ? `
        overflow: hidden;

        & ${Tile}::before {
            opacity: 0.5;
            animation: shimmer 2s infinite linear;
        }
    ` : ``};
`);

