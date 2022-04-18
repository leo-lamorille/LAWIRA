import './home.scss';
import BackgroundBanner from "./backgroundBanner";

export default function Home() {
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
                    text4="VOUS" text4Class="leftBannerBoldText"
                />
            </div>
            )
}