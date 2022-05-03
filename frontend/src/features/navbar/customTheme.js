import { createTheme } from "@material-ui/core/styles";

const customTheme = createTheme({
    overrides: {
        MuiAppBar: {
            colorPrimary: {
                backgroundColor: "rgb(37,41,64)",
                background: "linear-gradient(90deg, rgba(37,41,64,1) 0%, rgba(28,34,66,1) 50%, rgba(37,41,64,1) 100%)",
                color: "#ffffff",
            },
        },
    },
});

export default customTheme;