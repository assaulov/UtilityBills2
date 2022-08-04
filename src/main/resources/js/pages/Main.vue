<template>
    <v-container fluid fill-height>
        <div class="text-xs-center">
            <v-btn
                    x-large
                    color="pink"
                    dark
                    absolute
                    :style="{left: '50%', transform:'translateX(-50%)'}"
                    @click="bam">
                ПРИВЕТ
            </v-btn>

        </div>
    </v-container>
</template>

<script>
export default {
    name: 'Main',
    methods: {
        bam() {
            alert("Привет");
        }
    }
}
</script>

<style scoped>

</style>
