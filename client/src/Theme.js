import React from "react";
import { ThemeProvider } from "styled-components";
import { Mode } from "./App/MainPage/Header/Menu/ColorMode/ColorMode";

const fonts = ['Hind Guntur'];
const fontSizes = ['1rem', '2rem', '3rem'];
const fontWeights = [400, 700];

const theme = {
  colors: {
    tiles: {
      peach: '#ffc09f',
      lemon: '#ffee93',
      skin:  '#fcf5c7',
      lightBlue: '#a0ced9',
      lightGreen: '#adf7b6'
    },
    basic: {
      black: '#000000',
      white: '#FFFFFF'
    }
  },

  fonts,
  fontSizes,
  fontWeights
};

export const lightTheme = {
  name: Mode.LIGHT,
  fontColor: '#222',
  fontColorGray: '#484542',
  login: {
    backgroundColor: '#CEC8BC',
    tileColor: '#DCDCDC',
    fontColor: '#000000',
    buttonColor: '#44804F'
  },
  loginForm: {
    backgroundColor: '#DCDCDC'
  },
  header: {
    backgroundColor: '#CEC8BC',
    borderColor: '#111'
  },
  popover: {
    backgroundColor: '#DCDCDC'
  },
  tilesSection: {
    backgroundColor: '#CEC8BC',
  },
  tileSmall: {
    backgroundColor: '#E59D78'
  },
  tileSmallBoolean: {
    backgroundColor: '#DA4541'
  },
  tileMedium: {
    backgroundColor: '#8A7E70'
  },
  tileBigBar: {
    backgroundColor: '#979089',
    fontColor: '#FFFAE9',
  },
  tileBigLine: {
    backgroundColor: '#DCDCDC'
  },
}

export const darkTheme = {
  name: Mode.DARK,
  fontColor: '#CEC8BC',
  fontColorGray: '#CEC8BC',
  loginForm: {
    backgroundColor: '#484542'
  },
  header: {
    backgroundColor: '#222',
    borderColor: '#7D7D7D'
  },
  popover: {
    backgroundColor: '#484542'
  },
  tilesSection: {
    backgroundColor: '#222',
  },
  tileSmall: {
    backgroundColor: '#484542'
  },
  tileSmallBoolean: {
    backgroundColor: '#484542'
  },
  tileMedium: {
    backgroundColor: '#484542'
  },
  tileBigBar: {
    backgroundColor: '#484542',
    fontColor: '#DDD',
  },
  tileBigLine: {
    backgroundColor: '#484542'
  },
}

theme.fonts.main = 'Hind Guntur';

theme.fontSizes.p = fontSizes[0];
theme.fontSizes.h4 = fontSizes[1];
theme.fontSizes.h1 = fontSizes[2];

theme.fontWeights.normal = fontWeights[0];
theme.fontWeights.bold = fontWeights[1];

const Theme = ({ children, colorMode }) => (
    <ThemeProvider
      theme={{
        ...theme,
        colorMode: colorMode === Mode.LIGHT ? lightTheme : darkTheme
      }}
    >
      {children}
    </ThemeProvider>
);

export default Theme;

/*
Pastel color inspiration
https://venngage.com/blog/pastel-color-palettes/

Convention has been inspired from:
https://javascript.plainenglish.io/how-i-structure-styled-components-9f139adc9032
*/