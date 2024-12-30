import styled from "styled-components";

export const GradesDownloadIcon = styled.svg.attrs({
  version: "1.1",
  xmlns: "http://www.w3.org/2000/svg",
  xmlnsXlink: "http://www.w3.org/1999/xlink",
})`
  position: relative;
  height: 2rem;
  width: 2rem;
  border: none;
  padding: 0;
  transition: all 0.5s ease;
  cursor: pointer;
  fill: ${({ theme }) => theme.colorMode.fontColor};
`;

export const GradesDownloadAnchor = styled.a`
  position: relative;
  height: 2rem;
  border: none;
  transition: all 0.5s ease;
  cursor: pointer;
`;
