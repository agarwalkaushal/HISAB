package com.fullertonfinnovatica.Finance;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.R;

import static android.view.View.GONE;

public class FlexibleBudget extends AppCompatActivity {

    private EditText percent, units, vcUnit1, vcUnit2, vcUnit3,
            svTotal1, svTotal2, svTotal3, svTotal4,
            fTotal1, fTotal2,
            svFix1, svVar1, svFix2, svVar2,
            proprtion;

    private TextView vcUnitTotal, vcTotalTotal, svUnitTotal, svTotalTotal, fUnitTotal, fTotalTotal, unitTotal, totalTotal,
            percent1, units1,
            vcUnit1n, vcTotal1n, vcUnit2n, vcTotal2n, vcUnit3n, vcTotal3n,
            svUnit1n, svTotal1n, svUnit2n, svTotal2n, svUnit3n, svTotal3n, svUnit4n, svTotal4n,
            fUnit1n, fTotal1n, fUnit2n, fTotal2n,
            vcUnitTotaln, vcTotalTotaln, svUnitTotaln, svTotalTotaln, fUnitTotaln, fTotalTotaln, unitTotaln, totalTotaln,
            vcTotal1, vcTotal2, vcTotal3,
            fUnit1, fUnit2,
            svUnit1, svUnit2, svUnit3, svUnit4, svFixVar;

    private LinearLayout goClick;

    private Button go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible_budget);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Flexible Budget</font>"));

        goClick = findViewById(R.id.goClick);
        goClick.setVisibility(View.GONE);

        go = findViewById(R.id.go);

        percent = findViewById(R.id.percent);
        units = findViewById(R.id.units);

        percent1 = findViewById(R.id.percent1);
        units1 = findViewById(R.id.units1);

        vcUnit1 = findViewById(R.id.vcUnit1);
        vcUnit2 = findViewById(R.id.vcUnit2);
        vcUnit3 = findViewById(R.id.vcUnit3);
        vcTotal1 = findViewById(R.id.vcTotal1);
        vcTotal2 = findViewById(R.id.vcTotal2);
        vcTotal3 = findViewById(R.id.vcTotal3);
        vcTotalTotal = findViewById(R.id.vcTotalTotal);
        vcUnitTotal = findViewById(R.id.vcUnitTotal);

        vcUnit1n = findViewById(R.id.vcUnit1n);
        vcUnit2n = findViewById(R.id.vcUnit2n);
        vcUnit3n = findViewById(R.id.vcUnit3n);
        vcTotal1n = findViewById(R.id.vcTotal1n);
        vcTotal2n = findViewById(R.id.vcTotal2n);
        vcTotal3n = findViewById(R.id.vcTotal3n);
        vcTotalTotaln = findViewById(R.id.vcTotalTotaln);
        vcUnitTotaln = findViewById(R.id.vcUnitTotaln);

        svUnit1 = findViewById(R.id.svUnit1);
        svUnit2 = findViewById(R.id.svUnit2);
        svUnit3 = findViewById(R.id.svUnit3);
        svUnit4 = findViewById(R.id.svUnit4);
        svTotal1 = findViewById(R.id.svTotal1);
        svTotal2 = findViewById(R.id.svTotal2);
        svTotal3 = findViewById(R.id.svTotal3);
        svTotal4 = findViewById(R.id.svTotal4);
        svUnitTotal = findViewById(R.id.svUnitTotal);
        svTotalTotal = findViewById(R.id.svTotalTotal);

        svFix1 = findViewById(R.id.svFix1);
        svVar1 = findViewById(R.id.svVar1);
        svFix2 = findViewById(R.id.svFix2);
        svVar2 = findViewById(R.id.svVar2);

        svFixVar = findViewById(R.id.svFixVar);

        svUnit1n = findViewById(R.id.svUnit1n);
        svUnit2n = findViewById(R.id.svUnit2n);
        svUnit3n = findViewById(R.id.svUnit3n);
        svUnit4n = findViewById(R.id.svUnit4n);
        svTotal1n = findViewById(R.id.svTotal1n);
        svTotal2n = findViewById(R.id.svTotal2n);
        svTotal3n = findViewById(R.id.svTotal3n);
        svTotal4n = findViewById(R.id.svTotal4n);
        svUnitTotaln = findViewById(R.id.svUnitTotaln);
        svTotalTotaln = findViewById(R.id.svTotalTotaln);

        fUnit1 = findViewById(R.id.fUnit1);
        fUnit2 = findViewById(R.id.fUnit2);
        fTotal1 = findViewById(R.id.fTotal1);
        fTotal2 = findViewById(R.id.fTotal2);
        fTotalTotal = findViewById(R.id.fTotalTotal);
        fUnitTotal = findViewById(R.id.fUnitTotal);

        fUnit1n = findViewById(R.id.fUnit1n);
        fUnit2n = findViewById(R.id.fUnit2n);
        fTotal1n = findViewById(R.id.fTotal1n);
        fTotal2n = findViewById(R.id.fTotal2n);
        fTotalTotaln = findViewById(R.id.fTotalTotaln);
        fUnitTotaln = findViewById(R.id.fUnitTotaln);

        proprtion = findViewById(R.id.proportion);

        unitTotal = findViewById(R.id.unitTotal);
        unitTotaln = findViewById(R.id.unitTotaln);
        totalTotal = findViewById(R.id.totalTotal);
        totalTotaln = findViewById(R.id.totalTotaln);

        updateModifiedValues();

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkForNull()) {
                    if (proprtion.getText().toString().length() > 0 && Integer.parseInt(proprtion.getText().toString()) < 101) {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        Toast.makeText(getApplicationContext(),"Business output at "+proprtion.getText().toString()+ "% successfully generated",Toast.LENGTH_SHORT).show();
                        updateNewLayout();
                    } else {
                        proprtion.setError("Error");
                    }
                }
            }
        });

        units.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        vcUnit1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        vcUnit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        vcUnit3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        svTotal1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        svTotal2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        svTotal3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        svTotal4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        svFix1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        svVar1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        svFix2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        svVar2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        fTotal1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });
        fTotal2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateModifiedValues();
                }
            }
        });

    }

    private void updateNewLayout() {
        int newPercent = Integer.parseInt(proprtion.getText().toString());
        percent1.setText(String.valueOf(newPercent));
        int newUnits = Integer.parseInt(units.getText().toString()) * newPercent / Integer.parseInt(percent.getText().toString());
        units1.setText(String.valueOf(newUnits));

        vcUnit1n.setText(vcUnit1.getText());
        vcUnit2n.setText(vcUnit2.getText());
        vcUnit3n.setText(vcUnit3.getText());

        vcTotal1n.setText(String.valueOf(
                Integer.parseInt(vcUnit1n.getText().toString()) * newUnits
        ));

        vcTotal2n.setText(String.valueOf(
                Integer.parseInt(vcUnit2.getText().toString()) * newUnits
        ));

        vcTotal3n.setText(String.valueOf(
                Integer.parseInt(vcUnit3.getText().toString()) * newUnits
        ));

        vcUnitTotaln.setText(String.valueOf(
                Integer.parseInt(vcUnit1n.getText().toString()) +
                        Integer.parseInt(vcUnit2n.getText().toString()) +
                        Integer.parseInt(vcUnit3n.getText().toString())
        ));

        vcTotalTotaln.setText(String.valueOf(
                Integer.parseInt(vcTotal1n.getText().toString()) +
                        Integer.parseInt(vcTotal2n.getText().toString()) +
                        Integer.parseInt(vcTotal3n.getText().toString())
        ));

        svUnit1n.setText(String.valueOf(
                Integer.parseInt(svTotal1.getText().toString()) /
                        Integer.parseInt(units1.getText().toString())
        ));

        svTotal1n.setText(svTotal1.getText());

        svUnit2n.setText(svUnit2.getText());

        svTotal2n.setText(String.valueOf(
                Integer.parseInt(svUnit2.getText().toString()) *
                        Integer.parseInt(units1.getText().toString())
        ));

        svUnit3n.setText(String.valueOf(
                Integer.parseInt(svTotal3.getText().toString()) /
                        Integer.parseInt(units1.getText().toString())
        ));

        svTotal3n.setText(svTotal3.getText());

        svUnit4n.setText(svUnit4.getText());

        svTotal4n.setText(String.valueOf(
                Integer.parseInt(svUnit4.getText().toString()) *
                        Integer.parseInt(units1.getText().toString())
        ));

        svUnitTotaln.setText(String.valueOf(
                Integer.parseInt(svUnit1n.getText().toString()) +
                        Integer.parseInt(svUnit2n.getText().toString()) +
                        Integer.parseInt(svUnit3n.getText().toString()) +
                        Integer.parseInt(svUnit4n.getText().toString())
        ));

        svTotalTotaln.setText(String.valueOf(
                Integer.parseInt(svTotal1n.getText().toString()) +
                        Integer.parseInt(svTotal2n.getText().toString()) +
                        Integer.parseInt(svTotal3n.getText().toString()) +
                        Integer.parseInt(svTotal4n.getText().toString())
        ));

        fUnit1n.setText(String.valueOf(
                Integer.parseInt(fTotal1.getText().toString()) /
                        Integer.parseInt(units1.getText().toString())
        ));

        fUnit2n.setText(String.valueOf(
                Integer.parseInt(fTotal2.getText().toString()) /
                        Integer.parseInt(units1.getText().toString())
        ));

        fTotal1n.setText(fTotal1.getText());

        fTotal2n.setText(fTotal2.getText());

        fUnitTotaln.setText(String.valueOf(
                Integer.parseInt(fUnit1n.getText().toString()) +
                        Integer.parseInt(fUnit2n.getText().toString())
        ));

        fTotalTotaln.setText(String.valueOf(
                Integer.parseInt(fTotal1n.getText().toString()) +
                        Integer.parseInt(fTotal2n.getText().toString())
        ));

        unitTotaln.setText(String.valueOf(
                Integer.parseInt(vcUnitTotaln.getText().toString()) +
                        Integer.parseInt(svUnitTotaln.getText().toString()) +
                        Integer.parseInt(fUnitTotaln.getText().toString())
        ));

        totalTotaln.setText(String.valueOf(
                Integer.parseInt(vcTotalTotaln.getText().toString()) +
                        Integer.parseInt(svTotalTotaln.getText().toString()) +
                        Integer.parseInt(fTotalTotaln.getText().toString())
        ));


        goClick.setVisibility(View.VISIBLE);
    }

    private boolean checkForNull() {
        if (percent.getText().toString().length() > 0) {
            if (Integer.parseInt(percent.getText().toString()) < 101) {
                if (units.getText().toString().length() > 0) {
                    if (vcUnit1.getText().toString().length() > 0) {
                        if (vcUnit2.getText().toString().length() > 0) {
                            if (vcUnit3.getText().toString().length() > 0) {
                                if (svTotal1.getText().toString().length() > 0) {
                                    if (svTotal2.getText().toString().length() > 0) {
                                        if (svTotal3.getText().toString().length() > 0) {
                                            if (svTotal4.getText().toString().length() > 0) {
                                                if (svFix1.getText().toString().length() > 0 && Integer.parseInt(svFix1.getText().toString()) < 101) {
                                                    if (svFix2.getText().toString().length() > 0 && Integer.parseInt(svFix2.getText().toString()) < 101) {
                                                        if (svVar1.getText().toString().length() > 0 && Integer.parseInt(svVar1.getText().toString()) < 101) {
                                                            if (svVar2.getText().toString().length() > 0 && Integer.parseInt(svVar2.getText().toString()) < 101) {
                                                                if (fTotal1.getText().toString().length() > 0) {
                                                                    if (fTotal2.getText().toString().length() > 0) {
                                                                        return true;
                                                                    } else {
                                                                        fTotal2.setError("Null");
                                                                        return false;
                                                                    }
                                                                } else {
                                                                    fTotal1.setError("Null");
                                                                    return false;
                                                                }
                                                            } else {
                                                                svVar2.setError("Error");
                                                                return false;
                                                            }
                                                        } else {
                                                            svVar1.setError("Error");
                                                            return false;
                                                        }
                                                    } else {
                                                        svFix2.setError("Error");
                                                        return false;
                                                    }
                                                } else {
                                                    svFix1.setError("Error");
                                                    return false;
                                                }
                                            } else {
                                                svTotal4.setError("Null");
                                                return false;
                                            }
                                        } else {
                                            svTotal3.setError("Null");
                                            return false;
                                        }
                                    } else {
                                        svTotal2.setError("Null");
                                        return false;
                                    }
                                } else {
                                    svTotal1.setError("Null");
                                    return false;
                                }
                            } else {
                                vcUnit3.setError("Null");
                                return false;
                            }
                        } else {
                            vcUnit2.setError("Null");
                            return false;
                        }
                    } else {
                        vcUnit1.setError("Null");
                        return false;
                    }
                } else {
                    units.setError("Null");
                    return false;
                }
            } else {
                percent.setError("Cannot exceed 100");
                return false;
            }
        } else {
            percent.setError("Null");
            return false;
        }
    }

    private void updateModifiedValues() {

        if (checkForNull()) {

            vcTotal1.setText(String.valueOf(
                    Integer.parseInt(vcUnit1.getText().toString()) *
                            Integer.parseInt(units.getText().toString())
            ));

            vcTotal2.setText(String.valueOf(
                    Integer.parseInt(vcUnit2.getText().toString()) *
                            Integer.parseInt(units.getText().toString())
            ));

            vcTotal3.setText(String.valueOf(
                    Integer.parseInt(vcUnit3.getText().toString()) *
                            Integer.parseInt(units.getText().toString())
            ));

            svUnit1.setText(String.valueOf(
                    (Integer.parseInt(svTotal1.getText().toString())+ Integer.parseInt(svTotal2.getText().toString()))*
                            Integer.parseInt(svFix1.getText().toString())/ (Integer.parseInt(units.getText().toString())*100)
            ));

            svUnit2.setText(String.valueOf(
                    (Integer.parseInt(svTotal1.getText().toString())+ Integer.parseInt(svTotal2.getText().toString()))*
                            Integer.parseInt(svVar1.getText().toString())/ (Integer.parseInt(units.getText().toString())*100)
                    ));

            svUnit3.setText(String.valueOf(
                    (Integer.parseInt(svTotal3.getText().toString())+ Integer.parseInt(svTotal4.getText().toString()))*
                            Integer.parseInt(svFix2.getText().toString())/ (Integer.parseInt(units.getText().toString())*100)
            ));

            svUnit4.setText(String.valueOf(
                    (Integer.parseInt(svTotal3.getText().toString())+ Integer.parseInt(svTotal4.getText().toString()))*
                            Integer.parseInt(svVar2.getText().toString())/ (Integer.parseInt(units.getText().toString())*100)
            ));

            fUnit1.setText(String.valueOf(
                    Integer.parseInt(fTotal1.getText().toString()) /
                            Integer.parseInt(units.getText().toString())
            ));

            fUnit2.setText(String.valueOf(
                    Integer.parseInt(fTotal2.getText().toString()) /
                            Integer.parseInt(units.getText().toString())
            ));

            vcUnitTotal.setText(String.valueOf(
                    Integer.parseInt(vcUnit1.getText().toString()) +
                            Integer.parseInt(vcUnit2.getText().toString()) +
                            Integer.parseInt(vcUnit3.getText().toString())
            ));

            vcTotalTotal.setText(String.valueOf(
                    Integer.parseInt(vcTotal1.getText().toString()) +
                            Integer.parseInt(vcTotal2.getText().toString()) +
                            Integer.parseInt(vcTotal3.getText().toString())
            ));

            svUnitTotal.setText(String.valueOf(
                    Integer.parseInt(svUnit1.getText().toString()) +
                            Integer.parseInt(svUnit2.getText().toString()) +
                            Integer.parseInt(svUnit3.getText().toString()) +
                            Integer.parseInt(svUnit4.getText().toString())
            ));

            svTotalTotal.setText(String.valueOf(
                    Integer.parseInt(svTotal1.getText().toString()) +
                            Integer.parseInt(svTotal2.getText().toString()) +
                            Integer.parseInt(svTotal3.getText().toString()) +
                            Integer.parseInt(svTotal4.getText().toString())
            ));

            fUnitTotal.setText(String.valueOf(
                    Integer.parseInt(fUnit1.getText().toString()) +
                            Integer.parseInt(fUnit2.getText().toString())
            ));

            fTotalTotal.setText(String.valueOf(
                    Integer.parseInt(fTotal1.getText().toString()) +
                            Integer.parseInt(fTotal2.getText().toString())
            ));

            unitTotal.setText(String.valueOf(
                    Integer.parseInt(vcUnitTotal.getText().toString()) +
                            Integer.parseInt(svUnitTotal.getText().toString()) +
                            Integer.parseInt(fUnitTotal.getText().toString())
            ));

            totalTotal.setText(String.valueOf(
                    Integer.parseInt(vcTotalTotal.getText().toString()) +
                            Integer.parseInt(svTotalTotal.getText().toString()) +
                            Integer.parseInt(fTotalTotal.getText().toString())
            ));
        }
    }
}
