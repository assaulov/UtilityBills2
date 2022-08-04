<template>
    <v-data-table
            :headers="headers"
            :items="meters"
            sort-by="coldWater"
            class="elevation-1"
    >
        <template v-slot:top>
            <v-toolbar
                    flat
            >
                <v-toolbar-title>Meters</v-toolbar-title>
                <v-divider
                        class="mx-4"
                        inset
                        vertical
                ></v-divider>
                <v-spacer></v-spacer>
                <v-dialog
                        v-model="dialog"
                        max-width="500px"
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                                color="primary"
                                dark
                                class="mb-2"
                                v-bind="attrs"
                                v-on="on"
                        >
                            New Meter
                        </v-btn>
                    </template>
                    <v-card>
                        <v-card-title>
                            <span class="text-h5">{{ formTitle }}</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-col
                                            cols="12"
                                            sm="6"
                                            md="4"
                                    >
                                        <v-text-field
                                                v-model="editedItem.meterDataWrite"
                                                label="meterDataWrite"
                                        ></v-text-field>
                                    </v-col>
                                    <v-col
                                            cols="12"
                                            sm="6"
                                            md="4"
                                    >
                                        <v-text-field
                                                v-model="editedItem.coldWater"
                                                label="coldWater"
                                        ></v-text-field>
                                    </v-col>
                                    <v-col
                                            cols="12"
                                            sm="6"
                                            md="4"
                                    >
                                        <v-text-field
                                                v-model="editedItem.hotWater"
                                                label="hotWater"
                                        ></v-text-field>
                                    </v-col>
                                    <v-col
                                            cols="12"
                                            sm="6"
                                            md="4"
                                    >
                                        <v-text-field
                                                v-model="editedItem.electricity"
                                                label="electricity"
                                        ></v-text-field>
                                    </v-col>
                                    <v-col
                                            cols="12"
                                            sm="6"
                                            md="4"
                                    >
                                        <v-text-field
                                                v-model="editedItem.gas"
                                                label="gas"
                                        ></v-text-field>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-card-text>

                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                                    color="blue darken-1"
                                    text
                                    @click="close"
                            >
                                Cancel
                            </v-btn>
                            <v-btn
                                    color="blue darken-1"
                                    text
                                    @click="save"
                            >
                                Save
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
                <v-dialog v-model="dialogDelete" max-width="500px">
                    <v-card>
                        <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
                            <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
                            <v-spacer></v-spacer>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-toolbar>
        </template>
        <template v-slot:item.actions="{ item }">
            <v-icon
                    small
                    class="mr-2"
                    @click="editItem(item)"
            >
                edit
            </v-icon>
            <v-icon
                    small
                    @click="deleteItem(item)"
            >
                delete
            </v-icon>
        </template>
        <template v-slot:footer>
            <v-btn
                    color="primary"
                    @click="getMeters"
            >
                Reset
            </v-btn>
        </template>

    </v-data-table>


</template>


<script>
import {mapActions, mapState} from "vuex";
import axios from "axios";
import meters from "../api/meters";

export default {
    name: "MetersTable",
    data: () => {
        return {
            dialog: false,
            dialogDelete: false,
            meters: [],
            headers: [
                {
                    text: 'meterDataWrite',
                    align: 'start',
                    sortable: false,
                    value: 'meterDataWrite',
                },
                {text: 'coldWater', value: 'coldWater'},
                {text: 'hotWater', value: 'hotWater'},
                {text: 'electricity', value: 'electricity'},
                {text: 'gas', value: 'gas'},
                {text: 'Actions', value: 'actions', sortable: false},
            ],
            editedIndex: -1,
            editedItem: {
                meterId: null,
                meterDataWrite: '',
                coldWater: 0,
                hotWater: 0,
                electricity: 0,
                gas: 0,
            },
        }
    },

    computed: {
        formTitle() {
            return this.editedIndex === -1 ? 'New Meters' : 'Edit Meters'
        },
        ...mapState(['user']),

    },

    watch: {
        dialog(val) {
            val || this.close()
        },
        dialogDelete(val) {
            val || this.closeDelete()
        },
    },

    mounted() {
        this.getMeters()
    },
    created() {
        this.getMeters()
    },

    methods: {
        ...mapActions(['addMeterAction', 'getMeterAction', 'deleteMeterAction', 'updateMeterAction']),
        ...mapState(['user']),
        initialize() {

            // this.$store.dispatch('getMeterAction')

            // axios.get("http://localhost:8888/bills/meters/"+this.$store.state.user.login).then((response) => {
            //   console.log(response);
            //   this.meters = response.data;
            //   console.log(this.meters)
            // });

            // this.meters = [
            //   {
            //     "meterId": 3,
            //     "meterDataWrite": "21.01.2021",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 4,
            //     "meterDataWrite": "22.01.2021",
            //     "coldWater": 1.0,
            //     "hotWater": 2.0,
            //     "electricity": 3.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 5,
            //     "meterDataWrite": "15.04.1994",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 6,
            //     "meterDataWrite": "12.12.2020",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 7,
            //     "meterDataWrite": "12.04.1995",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 8,
            //     "meterDataWrite": "05.04.1994",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 9,
            //     "meterDataWrite": "08.04.1994",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 10,
            //     "meterDataWrite": "09.04.1994",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 11,
            //     "meterDataWrite": "21.01.2020",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 12,
            //     "meterDataWrite": "29.01.2020",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 13,
            //     "meterDataWrite": "22.01.2036",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 14,
            //     "meterDataWrite": "22.10.2039",
            //     "coldWater": 1.0,
            //     "hotWater": 2.0,
            //     "electricity": 3.0,
            //     "gas": 4.0
            //   },
            //   {
            //     "meterId": 15,
            //     "meterDataWrite": "20.09.2563",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 16,
            //     "meterDataWrite": "01.01.2001",
            //     "coldWater": 1.0,
            //     "hotWater": 2.0,
            //     "electricity": 3.0,
            //     "gas": 4.0
            //   },
            //   {
            //     "meterId": 17,
            //     "meterDataWrite": "02.01.2026",
            //     "coldWater": 0.0,
            //     "hotWater": 0.0,
            //     "electricity": 0.0,
            //     "gas": 0.0
            //   },
            //   {
            //     "meterId": 18,
            //     "meterDataWrite": "29.10.2021",
            //     "coldWater": 14.0,
            //     "hotWater": 0.0,
            //     "electricity": 600.0,
            //     "gas": 115.0
            //   }
            // ]
        },

        editItem(item) {
            this.editedIndex = this.meters.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem(item) {
            this.editedItem.meterId = item.meterId
            this.dialogDelete = true
        },

        async deleteItemConfirm() {
            const meter = {
                meterId: this.editedItem.meterId,
                userLogin: this.user.login.toString()
            }
            await this.deleteMeterAction(meter)
            await this.getMeters()
            this.closeDelete()
            this.window.refresh()
        },

        close() {
            this.dialog = false
            this.editedItem.meterId = null
            this.editedItem.meterDataWrite = null
            this.editedItem.userLogin = null
            this.editedItem.coldWater = null
            this.editedItem.hotWater = null
            this.editedItem.electricity = null
            this.editedItem.gas = null
            this.getMeters()
        },

        closeDelete() {
            this.dialogDelete = false
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem)
                this.editedIndex = -1
            })
        },

        async save() {
            const meter = {
                userLogin: this.user.login,
                meterDataWrite: this.editedItem.meterDataWrite,
                coldWater: this.editedItem.coldWater,
                hotWater: this.editedItem.hotWater,
                electricity: this.editedItem.electricity,
                gas: this.editedItem.gas
            }
            console.log(meter)
            if (this.editedItem.meterId != null) {
                meter.meterId = this.editedItem.meterId
                await this.updateMeterAction(meter)
            } else {
                await this.addMeterAction(meter, this.$store.state.user)
            }
            this.editedItem.meterId = null
            this.editedItem.meterDataWrite = null
            this.editedItem.userLogin = null
            this.editedItem.coldWater = null
            this.editedItem.hotWater = null
            this.editedItem.electricity = null
            this.editedItem.gas = null
            this.close()
        },

        async getMeters() {
            await this.getMeterAction(this.$store.state.user)
            this.meters = this.$store.state.metersList
        },
    }
}
</script>

<style scoped>

</style>
