import i18n from "i18next";
import { initReactI18next } from "react-i18next";

i18n
    .use(initReactI18next)
    .init({
        fallbackLng: "en",
        lng: "pl",
        interpolation: {
            escapeValue: false,
        },
        resources: {
            en: {
                translation: {
                    loginWelcome: "Welcome to StudentStats!",
                    loginChooseMonit: "Choose your university",
                    loginLoading: "Loading supported universities...",
                    loginUSOS: "Log in with USOS",
                    loginDemo: "Check out demo",
                    loginErrorUni: "Error loading universities",
                    loginError: "Can't connect to the server.",
                    loginInfo: "StudentStats is a platform created by <0>WMS_DEV</0> to allow students to browse their statistics in an accessible and attractive form. Log in with your USOS data and check how you are doing at university.",
                    menuTipColorMode: "Theme",
                    menuTipLanguageMode: "Zmień język",
                    menuTipFilters: "Filters",
                    menuTipPersonalData: "Your data",
                    menuTipGrades: "Grades CSV",
                    profileIndex: "Index number:",
                    profileMajor: "Major:",
                    profileStudentStatus: "Student status:",
                    profileStudiesStage: "Studies stage:",
                    profileStudiesType: "Studies type:",
                    profileFaculty: "Faculty:",
                    profileActive: "Active",
                    profileInactive: "Inactive",
                    filterTitle: "Filters",
                    filterSize: "Size",
                    filterCategory: "Category",
                    paragraph404: "Oops! We couldn't find that page, but we have a cool quiz for you.",
                    back404: "Back to login",
                    triviaTitle: "PWr Quiz",
                    triviaSubtitle: "How well do you know PWr?",
                    triviaPrompt: "Choose wisely...",
                    triviaHighscore: "That's {{ streak }} in a row!",
                    triviaMotivationMessages: {
                        happy: ["Super!", "Great!", "Awesome!", "Bravo!", "Good!", "Yes!"],
                        sad: ["No.", "Wrong!!!", "Error...", "No!", "Oh no!", "OoF!", "No way!"]
                    },
                    triviaNextMessages: ["Next?", "Again?", "Another one?", "Come on, one more...", "One more?", "Next question?", "Another question?"]
                },
            },
            pl: {
                translation: {
                    loginWelcome: "Witaj w StudentStats!",
                    loginChooseMonit: "Wybierz swoją uczelnię",
                    loginLoading: "Wczytywanie wspieranych uczelni...",
                    loginUSOS: "Zaloguj się przez USOS",
                    loginDemo: "Sprawdź demo",
                    loginErrorUni: "Błąd wczytywania uczelni",
                    loginError: "Błąd połączenia z serwerem.",
                    loginInfo: "StudentStats to platforma stworzona przez <0>WMS_DEV</0> w celu umożliwienia studentom przeglądania swoich statystyk w przystępnej i atrakcyjnej formie. Zaloguj się swoimi danymi do USOSa i sprawdź jak Ci idzie na studiach.",
                    menuTipColorMode: "Motyw",
                    menuTipLanguageMode: "Change language",
                    menuTipFilters: "Filtry",
                    menuTipPersonalData: "Twoje dane",
                    menuTipGrades: "CSV z ocenami",
                    profileIndex: "Numer indeksu:",
                    profileMajor: "Kierunek:",
                    profileStudentStatus: "Status studenta:",
                    profileStudiesStage: "Stopień studiów:",
                    profileStudiesType: "Rodzaj studiów:",
                    profileFaculty: "Wydział:",
                    profileActive: "Aktywny",
                    profileInactive: "Nieaktywny",
                    filterTitle: "Filtry kafelków",
                    filterSize: "Rozmiar",
                    filterCategory: "Kategoria",
                    paragraph404: "Oops! Nie znaleźliśmy tej strony, ale za to mamy fajny quiz.",
                    back404: "Wróć do logowania",
                    triviaTitle: "PWr Quiz",
                    triviaSubtitle: "Jak dobrze znasz PWr?",
                    triviaPrompt: "Wybierz mądrze...",
                    triviaHighscore: "To już {{ streak }} dobrze z rzędu!",
                    triviaMotivationMessages: {
                        happy: ["Super!", "Świetnie!", "Wspaniale!", "Brawo!", "Dobrze!", "Tak!"],
                        sad: ["Nie.", "Źle!!!", "Błąd...", "Nie!", "Oj źle!", "OoF!", "No nie!"]
                    },
                    triviaNextMessages: ["Kolejne?", "Jeszcze raz?", "Następne?", "No weź jeszcze raz...", "Jeszcze jedno?", "Kolejne pytanie?", "Następne pytanie?"]
                },
            }
        }});

export default i18n;