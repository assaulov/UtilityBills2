import SockJS from 'sockjs-client'
import {Stomp} from "@stomp/stompjs";


let stompClient = null
const handlers = []

export function connect() {
    const socket = new SockJS('/websocket')
    stompClient = Stomp.over(socket)
    stompClient.debug = () => {}
    stompClient.connect({},  () => {
        stompClient.subscribe('/topic/app', message => {
            handlers.forEach(handler => handler(JSON.parse(message.body)))
        })
    })
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}