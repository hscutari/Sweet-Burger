package com.dextra.sweetburger.utilities;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by henriquescutari on 8/7/17.
 */

public interface IBaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
