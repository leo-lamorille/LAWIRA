import { createTheme } from "@material-ui/core/styles";

const customTheme = createTheme({
    overrides: {
        MuiAppBar: {
            colorPrimary: {
                backgroundColor: "#262626",
                color: "#ffffff",
            },
        },
    },
});

export default customTheme;