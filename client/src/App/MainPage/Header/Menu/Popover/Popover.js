import React from 'react';
import { useEffect, useRef } from 'react';
import * as S from "./styles";
import { Menu } from "../Menu";

const mountedStyle = { animation: "inAnimation 250ms ease-in" };
const unmountedStyle = {
    animation: "outAnimation 270ms ease-out",
    animationFillMode: "forwards"
};


export const Popover = ({ setShowPopover, isPopoverMounted, menuRef, switchVisibility, children }) => {
    const wrapperRef = useRef(null);

    useEffect(() => {
        function handleClickOutside(event) {
            if (wrapperRef.current && !wrapperRef.current.contains(event.target) && !menuRef.current.contains(event.target)) {
                switchVisibility();
            }
        }
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    });

    return <S.Popover ref={wrapperRef} style={isPopoverMounted ? mountedStyle : unmountedStyle}
        onAnimationEnd={() => { if (!isPopoverMounted) setShowPopover(false) }}>
        {children}
    </S.Popover>

}