import styled from 'styled-components';
// import theme from '../../../Theme';

export const TilesSection = styled.div(({theme}) => `
    padding: 7vh 8vw;
    height: calc(100vh - 4rem);
    width: 100%;
    position: relative;
    top: 4.1rem;

    //Background
    opacity: 0.8;
    z-index: 1;

    //Tiles
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    // grid-template-rows: repeat(3, 25vh);
    grid-auto-flow: row;
    grid-auto-rows: 25vh;
    row-gap: 2rem;
    column-gap: 2rem;
    @media (max-width: 1000px) {
        column-gap: 1vh;
        row-gap: 2vh;
        grid-template-columns: repeat(2, 1fr);
    }

    overflow: scroll;
    overflow-x: hidden;
`);

export const Tile = styled.div`
    position: relative;
    overflow: hidden;
    border-radius: 10px;
    background-color: ${props => props.theme.colors.tiles.peach};
    color: ${({ theme }) => theme.colorMode.fontColor};
    height: 100%;
    width: 100%;
    box-shadow: rgba(0, 0.45, 0.45, 0.45) 0px 15px 20px -20px;
    transition: background-color .2s ease-in-out, color .2s ease-in-out;
    &:hover {
        cursor: pointer;
    }
    animation: fadeTIleIn 400ms ease-in-out;

    @keyframes fadeTIleIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }
`

