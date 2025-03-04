import { useRef, useEffect } from 'react';
import * as S from './styles';
import logoMain from './MainPage/Header/Logo/logo_main_HD.png';

export function HomeBackground() {
    const canvasRef = useRef(null);
    const logosRef = useRef([]);
    const requestIdRef = useRef(null);

    useEffect(() => {        
        const imageLoad = new Image();
        imageLoad.src = logoMain;
        imageLoad.onload = () => {
            setupLogos();
            requestIdRef.current = requestAnimationFrame(tick);
        };

        function setupLogos() {
            const logos = logosRef.current;
            while (logos.length > 0) logos.pop();

            // calculate amount of logos to render on x and y axis
            const xAmount = Math.min(Math.ceil(window.innerWidth / 500), 3);
            const xAxisGap = window.innerWidth / xAmount;

            const yAmount = Math.ceil(window.innerHeight / 300);
            const yAxisGap = window.innerHeight / yAmount;

            // calculate amount outside of the screen
            const xAmountEdges = xAmount + 2;
            const yAmountEdges = yAmount + 2;
        
            for (let i = 0; i < xAmountEdges * yAmountEdges; i++) {
                const x = xAxisGap * (i % xAmountEdges - 0.5);
                const y = yAxisGap * (Math.floor(i / xAmountEdges) - 0.5);
                
                const width = (0.5 + Math.random() * 0.5) * xAxisGap;
                const height = width * imageLoad.height / imageLoad.width;

                const rotation = Math.random() * 40 - 20;
                const opacity = Math.random() * 0.4 + 0.05;
    
                const xModifier = 0;
                const yModifier = 0;
        
                logos.push({ x, y, width, height, rotation, opacity, xModifier, yModifier });
            }
        }

        // render frame
        function renderFrame() {
            const logos = logosRef.current;
            canvasRef.current.width = window.innerWidth;
            canvasRef.current.height = window.innerHeight;
            const ctx = canvasRef.current.getContext("2d");
            for (const logo of logos) {
                const { x, y, width, height, rotation, opacity, xModifier, yModifier } = logo;
                ctx.save();
                ctx.translate(x + xModifier , y + yModifier);
                ctx.rotate((rotation * Math.PI) / 180);
                ctx.globalAlpha = opacity; 
                ctx.drawImage(imageLoad, -width/2, -height/2, width, height);
                ctx.restore();
            }
        };
    
        let pointerX = 0, pointerY = 0;
        const easeAmount = 20;
        const logoSpeed = 5;
        // function to run animation loop
        function tick() {
            if (!canvasRef.current) return;
            renderFrame();
        
            // Update the logos
            const logos = logosRef.current;
            for (const logo of logos) {
                // Calculate the desired position
                const targetX = (pointerX - logo.x) * logo.opacity / logoSpeed;
                const targetY = (pointerY - logo.y) * logo.opacity / logoSpeed;
        
                // Lerp towards the target position
                logo.xModifier += (targetX - logo.xModifier) / easeAmount;
                logo.yModifier += (targetY - logo.yModifier) / easeAmount;
            }
        
            requestIdRef.current = requestAnimationFrame(tick);
        };

        // resize event listener
        window.addEventListener('resize', handleResize);

        let timer;
        function handleResize() {
            if(timer) clearTimeout(timer);
            timer = setTimeout(setupLogos, 500);
        } 

        const prefersReducedMotion = window.matchMedia(`(prefers-reduced-motion: reduce)`) === true || window.matchMedia(`(prefers-reduced-motion: reduce)`).matches === true;
        
        if (!prefersReducedMotion) {
            // background movement
            // initially mousemove, after motion detected, deviceorientation
            document.addEventListener('mousemove', handleMouseMove);
            if(window.DeviceMotionEvent) {
                window.addEventListener("devicemotion", (e) => {
                    if(!canvasRef.current || e.rotationRate.alpha === null || e.rotationRate.beta === null || e.rotationRate.gamma === null) return;
                    canvasRef.current.animate({
                        opacity: [1, 0, 1],
                        offset: [0, 0.5, 1],
                    }, {duration: 1000, easing: "linear"})
                    setTimeout(() => {
                        window.addEventListener("deviceorientation", handleInitialOrientation, {once: true});
                        document.removeEventListener('mousemove', handleMouseMove);
                    }, 400);
                }, {once: true});
            }
        } 

        function handleMouseMove(event) {
            pointerX = event.clientX;
            pointerY = event.clientY;
        }

        let initXOrientation = null, initYOrientation = null;
        function handleInitialOrientation(event) {
            initXOrientation = event.gamma;
            initYOrientation = event.beta;
            window.addEventListener("deviceorientation", handleOrientation);
        }

        function handleOrientation(event) {
            let xOrientation = event.gamma;
            let yOrientation = Math.max(Math.min(event.beta, 90), -90);
            
            pointerX = (initXOrientation - xOrientation) * window.innerWidth / 30;
            pointerY = (initYOrientation - yOrientation) * window.innerHeight / 60;
        }

        return () => {
            cancelAnimationFrame(requestIdRef.current);
            window.removeEventListener('resize', handleResize);
            document.removeEventListener('mousemove', handleMouseMove);
            window.removeEventListener("deviceorientation", handleOrientation);
            window.removeEventListener("deviceorientation", handleInitialOrientation);
        };
        
    }, []); // <-- Add empty dependency array here

    return <S.Canvas ref={canvasRef} width={window.innerWidth} height={window.innerHeight} />;
}