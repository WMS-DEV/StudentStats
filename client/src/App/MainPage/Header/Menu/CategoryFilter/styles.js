import styled from "styled-components";

export const CategoryFilterButton = styled.button`
    position: relative;
    height: 1.5rem;
    width: 2rem;
    border: none;
    padding: 0;
    background-color: transparent;
    transition: all .5s ease;
    cursor: pointer;
    vertical-align: middle
`

export const FilterIcon = styled.svg.attrs({
    version: '1.1',
    xmlns: 'http://www.w3.org/2000/svg',
    xmlnsXlink: 'http://www.w3.org/1999/xlink',
  })`
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    transform-origin: center;
    transition: inherit;
    fill: ${({ theme }) => theme.colorMode.fontColor};
`