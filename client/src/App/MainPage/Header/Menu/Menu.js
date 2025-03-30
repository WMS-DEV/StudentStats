import * as S from "./styles";
import { useState, useRef } from "react";
import { CategoryFilter } from "./CategoryFilter/CategoryFilter";
import { ColorMode } from "./ColorMode/ColorMode";
import { LanguageMode } from "./LanguageMode/LanguageMode";
import { PersonalData } from "./PersonalData/PersonalData";
import { LogoutButton } from "./Logout/Logout";
import { ProfilePopover } from "./Popover/ProfilePopover/ProfilePopover";
import { FilterPopover } from "./Popover/FilterPopover/FilterPopover";
import { GradesDownloadIcon } from "./GradesDownload/GradesDownload";
import { getGrades } from "../../../../AuthorizationProvider";

export const Menu = ({ loggedIn }) => {
  const wrapperRef = useRef(null);
  const gradesDownloadRef = useRef(null);
  const [isProfilePopoverMounted, setIsProfilePopoverMounted] = useState(false);
  const [showProfilePopover, setShowProfilePopover] = useState(false);

  const switchProfilePopoverVisibility = () => {
    setIsProfilePopoverMounted(!isProfilePopoverMounted);
    if (!showProfilePopover) {
      setShowProfilePopover(true);
      if (showFilterPopover) switchFilterPopoverVisibility();
    }
  };

  const [isFilterPopoverMounted, setIsFilterPopoverMounted] = useState(false);
  const [showFilterPopover, setShowFilterPopover] = useState(false);

  const switchFilterPopoverVisibility = () => {
    setIsFilterPopoverMounted(!isFilterPopoverMounted);
    if (!showFilterPopover) {
      setShowFilterPopover(true);
      if (showProfilePopover) switchProfilePopoverVisibility();
    }
  };

  const handleGradesDownload = async () => {
    const jwt = localStorage.getItem("jwt");
    const gradesBlob = await getGrades(jwt);
    if(!gradesBlob) return;
    const objectURL = URL.createObjectURL(gradesBlob);

    if (!gradesDownloadRef.current) {
      return;
    }

    gradesDownloadRef.current.href = objectURL;
    gradesDownloadRef.current.click();
  };

  return (
    <S.Menu ref={wrapperRef}>
      <ColorMode />
      <LanguageMode />
      {loggedIn && <CategoryFilter onClick={switchFilterPopoverVisibility} />}
      {loggedIn && (
        <GradesDownloadIcon
          ref={gradesDownloadRef}
          onClick={handleGradesDownload}
        />
      )}
      {loggedIn && <PersonalData onClick={switchProfilePopoverVisibility} disabled={true} />}
      {loggedIn && <LogoutButton />}
      {showProfilePopover && (
        <ProfilePopover
          setShowPopover={setShowProfilePopover}
          showPopover={showProfilePopover}
          isPopoverMounted={isProfilePopoverMounted}
          switchVisibility={switchProfilePopoverVisibility}
          menuRef={wrapperRef}
        />
      )}
      {showFilterPopover && (
        <FilterPopover
          setShowPopover={setShowFilterPopover}
          showPopover={showFilterPopover}
          isPopoverMounted={isFilterPopoverMounted}
          switchVisibility={switchFilterPopoverVisibility}
          menuRef={wrapperRef}
        />
      )}
    </S.Menu>
  );
};
