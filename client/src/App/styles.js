import styled from 'styled-components';
import { lightTheme } from '../Theme';

// linear-gradient(-225deg, #3D4E81 0%, #5753C9 48%, #6E7FF3 100%) removed
const backgrounds = [['120deg', '#a1c4fd', '#c2e9fb'], ['120deg', '#f093fb', '#f5576c'], ['135deg', '#667eea', '#764ba2'], ['-225deg', '#3D4E81', '#6E7FF3'], ['0deg', '#e6b980', '#eacda3']];
const getRandomBackground = () => {
  const randomIndex = Math.floor(Math.random() * backgrounds.length);
  return backgrounds[randomIndex];
};

const [PageBgDirection, PageBg1, PageBg2] = getRandomBackground();

export const HomePage = styled.div`
  @property --pageBg1 {
    syntax: "<color>";
    inherits: false;
    initial-value: ${PageBg1};
  }
  @property --pageBg2 {
    syntax: "<color>";
    inherits: false;
    initial-value: ${PageBg2};
  }
  @property --pageAngle {
    syntax: "<angle>";
    inherits: false;
    initial-value: ${PageBgDirection};
  }
  display: flex;
  flex-direction: column;
  align-items: center;
  align-content: center;
  justify-content: center;
  flex-wrap: wrap;
  height: calc(100vh - 4rem);
  translate: 0 4rem;
  overflow: hidden;
  background-color: ${({ theme }) => theme.colorMode.tilesSection.backgroundColor};
  transition: background-color .5s ease;

  * {
    height: auto;
  }
  `;

export const HomeForm = styled.form`
  background-color: ${({ theme }) => theme.colorMode.tileBigLine.backgroundColor};
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  align-items: center;
  justify-content: center;
  background-color: #fff;
  border-radius: 1.5vh;
  padding: 4vh;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.1);
  min-height: 40%;
  min-width: min(60vh, 60vw);
  max-width: min(500px, 90vw);
  margin-top: 2vh;
  margin-inline: 10px;
  //background: linear-gradient(to bottom, #323232 0%, #3F3F3F 40%, #1C1C1C 150%), linear-gradient(to top, rgba(255,255,255,0.40) 0%, rgba(0,0,0,0.25) 200%);
  background: ${({ theme }) => theme.colorMode.loginForm.backgroundColor};
  background-blend-mode: multiply;
  color: ${lightTheme.login.fontColor};
  transition: background .5s ease;
`;

export const Title = styled.h1`
  font-size: 2rem;
  font-weight: 600;
  margin-bottom: 4vh;
  color: ${({ theme }) => theme.colorMode.fontColor};
  transition: color .5s ease;
`;

export const Button = styled.button`

  position: relative;
  border: none;
  background-color: #DDDDDD;
  background: ${lightTheme.login.buttonColor};
  color: white;
  padding: 1em;
  border-radius: 5px;
  font-family: 'JetBrains Mono';
  font-size: 0.8rem;
  cursor: pointer;
  transition: .2s;
  // height: 7vh;
  // line-height: 3vh;
  user-select: none;
  overflow: hidden;
  text-decoration: none;

  @media (hover: hover) and (pointer: fine) {
      &:hover {
        background-color: #55a265;
    }
  }

  &.faded {
    color: white;
    border: 2px solid ${lightTheme.login.buttonColor};
    background: ${lightTheme.login.buttonColor};
    opacity: 0.5;
  }
  &.highlight, &.faded {
    pointer-events: none;
    cursor: default;
  }
  &.outline {
    border: 2px solid ${lightTheme.login.buttonColor};
    background: none;
    color: ${({ theme }) => theme.colorMode.fontColor};
    font-weight: bold;

    @media (hover: hover) and (pointer: fine) {
      &:hover {
        color: white;
        background: ${lightTheme.login.buttonColor};
    }
  }
  }

  ${props => props.isFontSmall ? 'font-size: 70%;' : ''}
`;

export const ErrorMessage = styled.p`
  color: #fc7970;
  font-weight: 900;
  margin-top: 1.25vh;
`;

export const Canvas = styled.canvas`
  position: absolute;
  width: 100%;
  height: 100vh;
  top: 0;
  z-index: 0;
  filter: contrast(0.5) opacity(0);
  animation: fadeIn 0.5s 0.1s linear forwards;

  @keyframes fadeIn {
    from {
      filter: contrast(0.5) opacity(0);
    }
    to {
      filter: contrast(0.5) opacity(1);
    }
  }
`;