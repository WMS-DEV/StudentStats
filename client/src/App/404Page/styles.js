  import styled from 'styled-components';
import { Button, HomePage} from '../styles';

export const HomePageFlexible = styled(HomePage)`
  @media (max-height: 870px) and (min-width: 750px) {
    flex-direction: row;
    align-items: center;
  }
`;

export const Info404 = styled.div`
  max-width: min(700px, 90vw);
  margin-top: 4rem;
  display: grid;
  grid-template-columns: 1fr 1fr;
  flex-direction: column;
  align-items: center;
  justify-items: center;
  @media (max-height: 870px) and (min-width: 750px) {
    grid-template-columns: 1fr;
  }

  ${ Button } {
    grid-column: 1 / -1;
  }
`;

export const LargeTitle = styled.h1`
  // margin-top: 4rem;
  font-size: 25vw;
  color: #484542;
  color: ${({ theme }) => theme.colorMode.fontColorGray };
  position: relative;
  text-align: center;
  @media (min-width: 750px) {
    font-size: 190px;
  }
`;

export const TriviaTitle = styled.h1`
  color: ${({ theme }) => theme.colorMode.fontColor };
  display: inline;
  text-align: left;
  font-size: 1.5rem;
  font-weight: 700;
  width: 100%;
  margin: 10px;s
`;

export const TriviaSubtitle = styled.h4`
    color: ${({ theme }) => theme.colorMode.fontColor };
    opacity: 0.5;
    text-align: left;
    font-size: 1.5rem;
    width: 100%;
`;

export const Trivia = styled.section`
  font-family: 'JetBrains Mono';
  flex-grow: 2;
  color: black;
  border-radius: 1.5vh;
  padding: 3vh;
  text-align: center;
  transition: background-color 0.2s ease-in-out;
  user-select: none;
`;

export const TriviaButtons = styled.section`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1vh;
  height: auto;

  ${Button} {
    margin: 0;
    line-height: normal;
  }

  ${Button}:last-of-type {
    grid-column: 1 / 3;
    animation: popup 0.2s ease-in-out;
  }

  @keyframes popup {
    0% {
      transform: scale(0);
    }
    100% {
      transform: scale(1);
    }
  }
`;

export const Question = styled.span`
  display: block;
  height: auto;
  padding: 1vh;
  margin-bottom: 2vh;
  font-weight: bold;
  color: ${({ theme }) => theme.colorMode.fontColor };
`;

export const ErrorMessage = styled.p`
  color: #a1c4fd;
  margin-top: 1.25vh;
`;

export const Paragraph = styled.p`
  color: black;
  margin: 2vh;
  text-align: center;
  max-width: 50%;
  color: ${({ theme }) => theme.colorMode.fontColor };
  position: relative;
  font-size: min(3.5vw, 1.6rem);
`;
