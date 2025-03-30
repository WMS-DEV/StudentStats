import styled from 'styled-components'

export const LogoutIcon = styled.svg.attrs({
    version: '1.1',
    xmlns: 'http://www.w3.org/2000/svg',
    xmlnsXlink: 'http://www.w3.org/1999/xlink',
  })`
    transition: inherit;
    width: 1.5rem;
    height: 1.5rem;
    fill: ${({ theme }) => theme.colorMode.fontColor};
`

export const LogoutButton = styled.button`
  width: 1.5rem;
  height: 1.5rem;
  border: none;
  position: relative;
  background-color: transparent;
  cursor: pointer;
`