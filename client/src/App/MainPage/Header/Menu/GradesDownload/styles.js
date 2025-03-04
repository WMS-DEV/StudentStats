import styled from "styled-components";

export const GradesDownloadIcon = styled.svg.attrs({
  version: "1.1",
  xmlns: "http://www.w3.org/2000/svg",
  xmlnsXlink: "http://www.w3.org/1999/xlink",
})`
  position: relative;
  height: 1.5rem;
  width: 2rem;
  border: none;
  padding: 0;
  transition: all 0.5s ease;
  fill: ${({ theme }) => theme.colorMode.fontColor};
`;

export const GradesDownloadAnchor = styled.a`
  position: relative;
  height: 1.5rem;
  border: none;
  transition: all 0.5s ease;
  cursor: pointer;

  &.disabled {
    opacity: .7;
    cursor: not-allowed;
  }
`;
