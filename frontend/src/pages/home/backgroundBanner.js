import './backgroundBanner.scss';
import {css} from "@emotion/react";

export default function BackgroundBanner(props) {
    return (
        <div>
            <img
                className="backgroundBanner"
                src={props.srcImg}
            />
            <p className={props.text1Class}>{props.text1}</p>
            <p className={props.text2Class}>{props.text2}</p>
            <p className={props.text3Class}>{props.text3}</p>
            <p className={props.text4Class}>{props.text4}</p>
        </div>
    );
}
