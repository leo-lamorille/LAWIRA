import {useState} from "react";
import './home.scss';
import BackgroundBanner from "./backgroundBanner";
import {css} from "@emotion/react";

export default function Home() {
    const [light, setLight] = useState(1);
    const [lightPic, setLightPic] = useState("../../../framePics/LIGHT_OFF_PIC.png");
    const [lightHalo, setLightHalo] = useState({})
    // <!-- src="../../../framePics/LIGHT_OFF_PIC.png" -->

    function Light(event){
        event.preventDefault();
        if (light === 1){
            setLight(0);
            setLightPic("../../../framePics/LIGHT_ON_PIC.png");
            setLightHalo({boxShadow: "28vw -10vw 40vw 14vw rgb(141 202 229 / 60%)"});
        } else {
            setLight(1);
            setLightPic("../../../framePics/LIGHT_OFF_PIC.png");
            setLightHalo({});
        }
    }

    return (
            <div>
                <BackgroundBanner
                    srcImg="../../../banners/LAWIRA_FIRST_BANNER.png"
                    text1="Choisissez la" text1Class="bannerText"
                    text2="SÉCURITÉ" text2Class="bannerBoldText"
                    text3="choisissez" text3Class="bannerText"
                    text4="LAWIRA" text4Class="bannerBoldTextEnd"
                />
                <div className="blackBlock"></div>
                <div className="first-part">
                    <p className="paragraph">pour toutes vos données !</p>
                </div>
                <BackgroundBanner
                    srcImg="../../../banners/LAWIRA_SECOND_BANNER.png"
                    text1="Avec notre support USB" text1Class="leftBannerText"
                    text2="LAWIRA" text2Class="leftBannerBoldText"
                    text3="vos données n'appartiennent qu'à" text3Class="leftBannerText"
                    text4="VOUS" text4Class="leftBannerBoldTextEnd"
                />
                <div className="second-part">
                    <p className="lightWire">
                        l
                    </p>
                    <div className="lightFrame">
                        <a href="" onClick={ Light }><img className="lightPic" src={lightPic}/></a>
                    </div>
                    <div className="frameAndDescription">
                        <div className="picFrame" style={lightHalo}>
                            <img src="../../../framePics/FIRST_FRAME_PIC.png"/>
                        </div>
                        <span className="picFrameText">
                            <p className="para1">Une capacité de stockage</p>
                            <p className="para2">EXTRAORDINAIRE</p>
                            <p className="para3">allant jusqu'à</p>
                            <p className="para4">1To</p>
                        </span>
                    </div>
                    <div className="endHomeText">
                        <p>Avec votre clef USB</p>
                        <p className="endBold">UNIQUE</p>
                        <p>et</p>
                        <p className="endBold">PERSONNALISABLE</p>
                        <p>alliez</p>
                        <p className="endBold">SEREINITÉ</p>
                        <p>et</p>
                        <p className="endBold">STYLE</p>
                        <p>grâce à son système</p>
                        <p className="endBold">D'EMPREINTE DIGITALE</p>
                    </div>

                </div>
            </div>
            )
}