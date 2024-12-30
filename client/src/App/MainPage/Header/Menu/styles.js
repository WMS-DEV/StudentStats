import styled from 'styled-components';

export const Menu = styled.div`
    height: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;

    & > * {
        margin-right: 3rem;
    }

    @media screen and (max-width: 500px) {
        & > * {
            margin-right: 7vw;
        }
    }
`