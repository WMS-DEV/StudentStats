import React, { useEffect, useState } from 'react';
import * as S1 from '../styles';
import * as S2 from './styles';
import { Header } from '../MainPage/Header/Header.js';
import { HomeBackground } from '../HomeBackground.js';
import questions from './trivia.json';
import { useTranslation } from "react-i18next";
import i18n from '../../Language.js';

const S = {
    ...S1,
    ...S2
};

const previousQuestionIds = [];
function getRandomQuestion(language) {
    const languageQuestions = questions[language];
    const questionIds = Object.keys(languageQuestions);
    let randomId = questionIds[Math.floor(Math.random() * questionIds.length)];

    let maxTries = 0;
    while (previousQuestionIds.includes(randomId) && maxTries < questionIds.length) {
        randomId = questionIds[Math.floor(Math.random() * questionIds.length)];
        maxTries++;
    }

    if (previousQuestionIds.length > questionIds.length - 1) {
       while(previousQuestionIds.length > 1) {
           previousQuestionIds.shift();
       }
    }
    previousQuestionIds.push(randomId);

    const question = languageQuestions[randomId];
    const answers = question.answers;
    const correctAnswer = question.correct;
    
    return [question.question, answers, correctAnswer];
}

function getCurrentQuestion(language) {
    const id = previousQuestionIds[previousQuestionIds.length - 1];
    const question = questions[language][id];
    const answers = question.answers;
    const correctAnswer = question.correct;
    
    return [question.question, answers, correctAnswer];
}

function getRandomMessage(t, wasCorrect, streak, streakText) {
    return `
        ${wasCorrect ?
            getRandomHappyMessage(t) :
            getRandomSadMessage(t)
        } 
        ${streak > 1 ? streakText : ''} 
        ${getRandomNextMessage(t)}
    `
} 

function getRandomHappyMessage(t) {
    const messages = t('triviaMotivationMessages', { returnObjects: true }).happy;
    return messages[Math.floor(Math.random() * messages.length)];
}

function getRandomSadMessage(t) {
    const messages = t('triviaMotivationMessages', { returnObjects: true }).sad;
    return messages[Math.floor(Math.random() * messages.length)];
}

function getRandomNextMessage(t) {
    const messages = t('triviaNextMessages', { returnObjects: true });
    return messages[Math.floor(Math.random() * messages.length)];
}

const TriviaButtons = ({ answers, correctAnswer, setQuestionId }) => {
    const [userAnswer, setUserAnswer] = useState(null);
    const [streak, setStreak] = useState(0);
    const [messageAfterAnswer, setMessageAfterAnswer] = useState('');
    const { t } = useTranslation();

    const checkAnswer = (user) => {
        if (userAnswer !== null) return;
        setUserAnswer(user);
        const isCorrect = user === correctAnswer;
        setStreak((oldStreak) => isCorrect ? oldStreak + 1 : 0);
        setMessageAfterAnswer(getRandomMessage(t, isCorrect, isCorrect ? streak + 1 : 0, t('triviaHighscore', { streak })));
    }
    
    const refreshButtons = () => {
        if (userAnswer === null) return;
        setUserAnswer(null);
        setMessageAfterAnswer('');
        setQuestionId((oldId) => oldId + 1);
    }

    const answerButtons = answers.map((answer, index) => {
        const isNoUserAnswer = userAnswer === null;
        const isCorrectAnswer = correctAnswer === index;
        const isSelectedAndIncorrect = userAnswer === index && userAnswer !== correctAnswer;
        const outlineClass = isSelectedAndIncorrect ? '' : 'outline';
        
        const className = isNoUserAnswer ? 'outline' : (isCorrectAnswer ? 'highlight' : `faded ${outlineClass}`);
        const isFontSmall = answer.length > 10;
        
        return <S.Button
            type="button"
            className={className}
            isFontSmall={isFontSmall}
            onClick={() => checkAnswer(index)}
            key={index}>
            {answer}
        </S.Button>
    })

    const actionButtonText =
        userAnswer === null ? t('triviaPrompt') : messageAfterAnswer;

    const actionButtonClass = userAnswer === null ? 'faded' : 'outline';
    return (
        <S.TriviaButtons>
            {answerButtons}
            <S.Button
                type="button"
                className={actionButtonClass}
                onClick={refreshButtons}>
                {actionButtonText}
            </S.Button>
        </S.TriviaButtons>
    )
}

const Trivia = () => {
    const [question, setQuestion] = useState(['', [], 0]);
    const [questionText, answers, correctAnswer] = question;
    const [questionId, setQuestionId] = useState(0);

    useEffect(() => {
        i18n.on('languageChanged', () => {
            setQuestion(getCurrentQuestion(i18n.language));
        });
    }, []);

    useEffect(() => {
        setQuestion(getRandomQuestion(i18n.language));
    }, [questionId]);

    return (
        <S.Trivia>
            <S.Question>{questionText}</S.Question>
            <TriviaButtons answers={answers} correctAnswer={correctAnswer} setQuestionId={setQuestionId}></TriviaButtons>
        </S.Trivia>
    )
}

export const Page404 = () => {
    const { t } = useTranslation();

    return (
    <>
        <Header loggedIn={false}/>
        <S.HomePageFlexible>
            <HomeBackground/>
            <S.Info404>
                <S.LargeTitle>404</S.LargeTitle>
                <S.Paragraph>{t('paragraph404')}</S.Paragraph>
                <S.Button as="a" href="/">{t('back404')}</S.Button>
            </S.Info404>
            <S.HomeForm>
                <S.TriviaTitle>{t('triviaTitle')}</S.TriviaTitle>
                <S.TriviaSubtitle>{t('triviaSubtitle')}</S.TriviaSubtitle>
                <Trivia />
            </S.HomeForm>
        </S.HomePageFlexible>
    </>
    );
};