import styled from "styled-components";
import { Button } from '../styles';
import { Spinner } from "react-bootstrap";

export const LoginPageButton = styled(Button)`
    width: 70%;
    white-space: normal;
    word-wrap: break-word;

    &:hover {
      background-color: #55a265;
      transition: background-color 0.3s ease;
    }

    &:active {
      background-color: #3C6B43;
    }

    &:disabled {
        filter: brightness(80%);
        cursor: not-allowed;
    }
` 

export const Message = styled.p`
  color: red;
  margin-bottom: 10px;
`

export const InfoBox = styled.div`
  border-radius: 10px;
  position: relative;
  width: 30px;
  background: ${({ theme }) => theme.colorMode.loginForm.backgroundColor};
  justify-content: center;
  align-items: center;
  margin: 10px;
  width: min(60vh, 60vw);
  max-width: 90vw;
  max-height: 90%;
  font-size: clamp(0.5rem, 5vw, 1rem);
  overflow-y: auto;

  border-radius: 10px;
  text-align: center;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 2;
  transition: background .5s ease, color .5s ease;

  @media screen and (max-width: 1024px) {
    width: 60vh;
  }
`

export const InfoMessage = styled.div`
  width: 100%;
  color: ${({ theme }) => theme.colorMode.fontColor};
  line-height: 1.25;
  padding: 10px;

  & a {
    color: ${({ theme }) => theme.colorMode.fontColor};
  }
`

export const Selection = styled.select`
    border: none;
    border-bottom: 2px solid #4d4d4d;
    color: ${({ theme }) => theme.colorMode.fontColor};
    padding: 0px 0.5vh;
    margin-bottom: 1.5vh;
    width: 95%;
    font-size: 1rem;
    text-align: center;
    transition: all 0.2s ease-in-out;
    background: transparent;
    background-blend-mode: multiply;

    &:focus {
        outline: none;
    }

    &:disabled {
      filter: brightness(80%);
      cursor: not-allowed;
    }

    &:-webkit-autofill,
    &:-webkit-autofill:focus {
        transition: background-color 0s 600000s, color 0s 600000s !important;
    }
`

export const Option = styled.option`
    width: 100%;
    color: ${({ theme }) => theme.colorMode.fontColor};
    background: ${({ theme }) => theme.colorMode.loginForm.backgroundColor};
`

export const LoadingSpinner = styled(Spinner)`
  width: 3rem;
  height: 3rem;
`;
