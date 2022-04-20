import {createSlice} from "@reduxjs/toolkit";

export const userSlice = createSlice({
   name: 'user',

    reducers: {
       setSub: (state, action) => {
           return {
               ...state, sub: action.payload
           }
       },

       setRole: (state, action) => {
           return {
               ...state, role: action.payload
           }
       },

       setExp: (state, action) => {
           return {
               ...state, exp: action.payload
           }
       },
        setIat: (state, action) => {
            return {
                ...state, iat: action.payload
            }
        },
        setJwtToken: (state, action) => {
           return {
               ...state, jwt: action.payload
           }
        }
    },

   initialState: {
       sub: '',
       role: '',
       exp: 0,
       iat: 0,
       jwt: ''
   },
});
